# Headless Architecture with Adobe Experience Manager

## Table of Contents

* [Lab Overview](/README.md#lab-overview)
* [Lesson 0 - Software Installation](/lessons/lesson-0.md)
* [Lesson 1 - Content Fragments](/lessons/lesson-1.md)
* [Lesson 2 - Assets HTTP API](/lessons/lesson-2.md)
* [Lesson 3 - Sling Model Exporter](/lessons/lesson-3.md)
* [Next Steps](#next-steps)
* [Appendix](Appendix.)

## Lesson 3 - Content Services: Sling Model Exporter

### Lesson Context

This lesson covers headless delivery of existing content in AEM using **Sling Model Exporter (SME)** . In this case we will try to expose Product Category Hierarchy as JSON so that our mobile app can consume that and display a mobile-first view to browsing products.

### Definition

***Sling Model Exporter (SME)*** - Apache Sling Models 1.3.0 introduces Sling Model Exporter, an elegant way to export or serialize Sling Model objects into custom abstractions

### Scenario

Compare the OOTB JSON from AEM with JSON created by a custom Sling Model Exporter and view the differences.

### Exercise 3.1 - OOTB Products JSON

#### 1. View the HTML Content
First let's look at the content we are trying to expose in HTML. Follow the link below to look at the products HTML

* [http://localhost:4503/content/we-retail/us/en/products.html]()


#### 2. View the JSON Output
Follow the link below to look at the products OOTB JSON:

* [http://localhost:4503/content/we-retail/us/en/products.json]()

> ***Note:*** The Default JSON output in AEM only gives information about the particular resource in question. You can get more information if you append a selector like .4.json or .5.json to go 4 to 5 levels deep from the particular resource.

#### 3. View the JSON Content at Depth
Follow the link below to look at the products OOTB JSON 5 levels deep:

* [http://localhost:4503/content/we-retail/us/en/products.5.json]()

> ***Pro Tip:*** From a security standpoint, we advise you not to use the .4.json or .infinity.json selectors because it could potentially slow down the server trying to output a whole lot of content un-intentionally. So if your Dispatcher was setup securely, you would not be able to view this URL from the publish side. This is just for educational purposes. For a more readable JSON you can use the 'tidy' selector as follows:
> [http://localhost:4503/content/we-retail/us/en/products.tidy.5.json]()

### Exercise 3.2 - Custom Sling Model Exporter generated JSON

Now let's compare the existing JSON output to the JSON output from our custom Sling Model Exporter as shown below:

* [http://localhost:4503/content/we-retail/us/en/products/_jcr_content.api.json]()

Now compare this JSON with the JSON from the previous step. What a world of difference some custom code can make. 

> ***Note:*** Sling Model Exporter attaches itself to a resource type and automatically gives us the ability to pick and choose any property available at that resource level. It also allows us to run some business logic and maybe even get related content not located at that particular resource. This is a very powerful and custom tool that can be used to navigate tricky content and still expose well-structured JSON to be used by 3rd party applications in a headless manner.

### Exercise 3.3 - Custom Sling Model Exporter Java Class

Let's navigate to and open up the Sling Model Exporter Java Class that is allowing us to expose all this information.

` ~/Desktop/Summit-2019/summit-L763-aem/core/src/main/java/com/adobe/summit/l763/models/ProductListModel.java `

Look at the Resource Type it attaches to. In this case 
` resourceType = "weretail/components/structure/page" `

Look at the Exporter extension
` extensions = "json" `

Look at the Exporter selector 
` selector = "api" `

> ***Pro-Tip 1:*** If you don't specify a selector, the default selector is "model" which is also the default selector for the Core Components and templates we used in Chapter 2. Best Practice is to specify a selector so you can have multiple JSON outputs for the same resource and avoid clashing with the OOTB .model.json selector.

> ***Pro-Tip 2:*** If you already have custom components that use Sling Models, By adding the Exporter annotations, you can easily convert these components into Core-like components where we can get structured JSON output when we hit the .model.json on the page containing the custom components.

### Exercise 3.4 - Preview Products in the Mobile App

Open up the Mobile App preview on your browser and navigate to the Products tab in the menu to see all the products being served from AEM using our custom Sling Model Exporter.

Take a look at the code at the following location:
` ~/Desktop/Summit-2019/app/src/pages/sme-overview/sme-overview.html `
