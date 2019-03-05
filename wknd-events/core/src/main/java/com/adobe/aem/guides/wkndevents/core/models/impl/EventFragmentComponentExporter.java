package com.adobe.aem.guides.wkndevents.core.models.impl;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.util.*;
import com.adobe.aem.guides.wkndevents.core.models.WkndEvent;

/**
 * This file focuses on making a Sling Model compatible with AEM Content
 * Services (and thus SPA Editor), as this requires: a) Implementing the
 * ComponentExporter interface.
 *
 * Note that these is a sibling interface to ComponentExporter,
 * com.adobe.cq.export.json.ContainerExporter, that is used for components that
 * included other components (like the Layout Container).
 *
 * For more detailed examples of Sling Models and Sling Model Exporters, see
 * this file's sibling sample files (SampleSlingModel.class and
 * SampleSlingModelExporter.class).
 */
@Model(
        // This must adapt from a SlingHttpServletRequest, since this is invoked
        // directly via a request, and not via a resource.
        // If can specify Resource.class as a second adaptable as needed
        adaptables = { SlingHttpServletRequest.class },

        // This Model should have the specific Model class
        // (SampleComponentExporter.class)
        // AS WELL AS the ComponentExporter.class. Its required that
        // ComponentExporter.class to be set at an adapter
        // since this how the AEM Content Services JSON Exporter figures out which
        // resources to serialize.
        adapters = { EventFragmentComponentExporter.class, ComponentExporter.class },

        // The resourceType is required if you want Sling to "naturally" expose this
        // model as the exporter for a Resource.
        resourceType = EventFragmentComponentExporter.RESOURCE_TYPE,

        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
// name = the registered name of the exporter (
// ExporterConstants.SLING_MODEL_EXPORTER_NAME => jackson )
// extensions = the extensions this exporter is registered to
// (ExporterConstants.SLING_MODEL_EXTENSION => json)
// selector = defaults to "model", can override as needed; This is helpful if a
// single resource needs 2 different JSON renditions.
// (ExporterConstants.SLING_MODEL_SELECTOR => model)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, selector = ExporterConstants.SLING_MODEL_SELECTOR, // The
                                                                                                                 // default
                                                                                                                 // is
                                                                                                                 // 'model',
                                                                                                                 // this
                                                                                                                 // is
                                                                                                                 // just
                                                                                                                 // reiterating
                                                                                                                 // this.
        extensions = ExporterConstants.SLING_MODEL_EXTENSION, options = { // options are optional... this just shows
                                                                          // that it is possible...
                /**
                 * Jackson options: - Mapper Features:
                 * http://static.javadoc.io/com.fasterxml.jackson.core/jackson-databind/2.8.5/com/fasterxml/jackson/databind/MapperFeature.html
                 * - Serialization Features:
                 * http://static.javadoc.io/com.fasterxml.jackson.core/jackson-databind/2.8.5/com/fasterxml/jackson/databind/SerializationFeature.html
                 */
                @ExporterOption(name = "MapperFeature.SORT_PROPERTIES_ALPHABETICALLY", value = "true"),
                @ExporterOption(name = "SerializationFeature.WRITE_DATES_AS_TIMESTAMPS", value = "false") })

// Mark as JSON Serializable as the Model's class
// (SampleComponentExporter.class) or interface if those are used.
@JsonSerialize(as = EventFragmentComponentExporter.class)

// Make sure the Model implementation implements (or the Model interface
// extends) com.adobe.cq.export.json.ComponentExporter.
public class EventFragmentComponentExporter implements ComponentExporter {
    /**
     * The resource type of the component associated with this Sling model.
     */
    static final String RESOURCE_TYPE = "wknd-events/components/content/eventfragment";
    static final String MASTER_PATH = "data/master";
    static final String FRAGMENT_PROP = "fragmentPath";

    private final Logger log = LoggerFactory.getLogger(EventFragmentComponentExporter.class);

    @Self
    private SlingHttpServletRequest request;

    private Resource resource;

    // Injection will occur over all Injectors based on Ranking;
    // Force an Injector using @Source(..)
    // If an Injector is not working; ensure you are using the latest version of
    // Sling Models
    @SlingObject
    @Required
    private ResourceResolver resourceResolver;

    public WkndEvent fragmentContent;
    private WkndEvent wkndEvent;

    @PostConstruct
    private void init() {
        log.info("In EventFragmentComponentExporter!!");
        resource = request.getResource();
        log.info("Current Resource is " + resource.getPath());
    }

    @Nonnull
    @Override
    public String getExportedType() {
        // This method is required by ComponentExporter and its value populates the
        // `:type` key in the JSON object.
        // The resource value is ~always the ResourceType for this model (See @Model(..)
        // above).
        return request.getResource().getResourceType();
    }

    public WkndEvent getFragmentContent() {

        String fragmentPath;
        ValueMap props = resource.getValueMap();
        if (props.containsKey(FRAGMENT_PROP)) {
            log.info("fragmentPath prop exists!");
            fragmentPath = props.get(FRAGMENT_PROP, "");
            if (fragmentPath != null) {
                log.info("fragmentPath is not null! -> " + fragmentPath);

                Resource fragmentResource = request.getResourceResolver()
                        .getResource(fragmentPath + "/jcr:content/data/master");

                if (fragmentResource != null) {
                    log.info("fragmentResource is not null! -> " + fragmentResource.getPath());
                    ValueMap fragmentProps = fragmentResource.getValueMap();
                    if (!(fragmentProps.isEmpty())) {
                        log.info("fragmentProps is not empty! -> " + fragmentProps.size());

                        String wkndArtistName = fragmentProps.get("wkndArtistName", "");
                        String[] wkndEventAddress = fragmentProps.get("wkndEventAddress", null);
                        String wkndEventCity = fragmentProps.get("wkndEventCity", "");
                        GregorianCalendar wkndEventDate = fragmentProps.get("wkndEventDate", null);

                        String wkndEventDescription = fragmentProps.get("wkndEventDescription", "");
                        String wkndEventImage = fragmentProps.get("wkndEventImage", "");
                        String[] wkndEventTimes = fragmentProps.get("wkndEventTimes", null);
                        String wkndEventTitle = fragmentProps.get("wkndEventTitle", "");

                        String[] wkndEventType = fragmentProps.get("wkndEventType", null);
                        Double wkndTicketPrice = fragmentProps.get("wkndTicketPrice", null);
                        String wkndVenueName = fragmentProps.get("wkndVenueName", "");

                        wkndEvent = new WkndEvent(wkndArtistName, wkndEventAddress, wkndEventCity, wkndEventDate,
                                wkndEventDescription, wkndEventImage, wkndEventTimes, wkndEventTitle, wkndEventType,
                                wkndTicketPrice, wkndVenueName);
                    }
                }
            }
        }
        return wkndEvent;
    }
}