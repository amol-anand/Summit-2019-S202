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

@Model(
        adaptables = { SlingHttpServletRequest.class },
        adapters = { SpeakerListComponentExporter.class, ComponentExporter.class },
        resourceType = SpeakerListComponentExporter.RESOURCE_TYPE,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
          selector = ExporterConstants.SLING_MODEL_SELECTOR, // The default is 'model', this is just reiterating this.
          extensions = ExporterConstants.SLING_MODEL_EXTENSION,
          options = {
                        @ExporterOption(name = "MapperFeature.SORT_PROPERTIES_ALPHABETICALLY", value = "true"),
                        @ExporterOption(name = "SerializationFeature.WRITE_DATES_AS_TIMESTAMPS", value="false")
          }
)

@JsonSerialize(as = SpeakerListComponentExporter.class)

// Make sure the Model implementation implements (or the Model interface extends) com.adobe.cq.export.json.ComponentExporter.
public class SpeakerListComponentExporter implements ComponentExporter {
    static final String RESOURCE_TYPE = "wknd-events/components/content/speakerlist";

    private final Logger log = LoggerFactory.getLogger(SpeakerListComponentExporter.class);

    @Self
    private SlingHttpServletRequest request;

    private Resource resource;

    // Injection will occur over all Injectors based on Ranking;
    // Force an Injector using @Source(..)
    // If an Injector is not working; ensure you are using the latest version of Sling Models
    @SlingObject
    @Required
    private ResourceResolver resourceResolver;

    private String jsonPath;
    private String orderBy;
    private String sortOrder;
    private String maxItems;


    @PostConstruct
    private void init() {
        log.info("In SpeakerListComponentExporter!!");
        resource = request.getResource();
        log.info("Current Resource is "+resource.getPath());
    }

    @Nonnull
    @Override
    public String getExportedType() {
        // This method is required by ComponentExporter and its value populates the `:type` key in the JSON object.
        // The resource value is ~always the ResourceType for this model (See @Model(..) above).
        return request.getResource().getResourceType();
    }

    public String getJsonPath(){
        jsonPath = "";
        ValueMap props = resource.getValueMap();
        if(props.containsKey("pages")){
            jsonPath = props.get("pages", "");
            if(jsonPath != null){
                jsonPath = jsonPath.replace("/content/dam","/api/assets") + ".json";
                log.info("jsonPath is "+jsonPath);
                return jsonPath;
            }
        }
        return jsonPath;
    }

    public String getOrderBy() {
        orderBy = "";
        ValueMap props = resource.getValueMap();
        if (props.containsKey("orderBy")) {
            orderBy = props.get("orderBy", "");
        }
        return orderBy;
    }

    public String getSortOrder() {
        sortOrder = "";
        ValueMap props = resource.getValueMap();
        if (props.containsKey("sortOrder")) {
            sortOrder = props.get("sortOrder", "");
        }
        return sortOrder;
    }

    public String getMaxItems() {
        maxItems = "0";
        ValueMap props = resource.getValueMap();
        if (props.containsKey("maxItems")) {
            maxItems = props.get("maxItems", "0");
        }
        return maxItems;
    }
    
}