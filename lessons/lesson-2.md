# Headless Architecture with Adobe Experience Manager

## Table of Contents

* [Lab Overview](/README.md#lab-overview)
* [Lesson 0 - Software Installation](/lessons/lesson-0.md)
* [Lesson 1 - Content Fragments](/lessons/lesson-1.md)
* [Lesson 2 - Assets HTTP API](/lessons/lesson-2.md)
* [Lesson 3 - Sling Model Exporter](/lessons/lesson-3.md)
* [Next Steps](#next-steps)
* [Appendix](Appendix.)

## Lesson 2 - Content Fragments via Assets HTTP API

### Lesson Context

This lesson covers headless delivery of content in AEM Assets via the **Assets HTTP API**. In this case we will explore the differences between the Content Fragment API page exposed model and the Assets API model. 

### Definition

***Assets HTTP API*** - The Adobe Experience Manager (AEM) Assets HTTP API allows developers to access content (stored in AEM) directly over the HTTP API, via CRUD operations (Create, Read, Update, Delete).  

The API allows you to operate AEM as a headless CMS (Content Management System) by providing Content Services to a JavaScript front end application. Or any other application that can execute HTTP requests and handle JSON responses.

### Scenario

Compare the Content Fragment API Page model JSON with JSON exposed by the Assets REST API and view the differences.

### Exercise 2.1 - FAQ Content Services API Page JSON Model

#### 1. FAQ Page - HTML
First let's look at the content we are trying to expose in HTML. Follow the link below to look at the FAQ HTML.
  * [http://localhost:4503/content/we-retail/us/en/api/faq.html]()

#### 2. FAQ Page - JSON
Follow the link below to look at the FAQ API Page JSON.

  * [http://localhost:4503/content/we-retail/us/en/api/faq.model.json]()

> ***Note:*** Look at all of the additional properties of the page and it's children Content Fragment components. These are properties of the page and components, which aren't necessary for most consuming applications.

#### 3. FAQ Page Mobile View 
Finally, let's take a look at the view of these FAQs in the mobile application. Select the `FAQ - Page` option:

<p align="center">
  <img src="/images/faq-cf-page-menu.png" width="300px" />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="/images/faq-cf-page-app.png" width="300px" />
</p>


### Exercise 2.2 - Assets HTTP API generated JSON

#### 1. FAQ Assets HTTP API JSON
Now let's compare the API Page's JSON to the new Content Fragment REST API JSON. You can see much less information listed here. All that exists is the information about the individual FAQs. Do you notice anything *not* available on this API that is on the other one? 

  * [http://localhost:4503/api/assets/summit-lab-763/faq-fragments.json]()

***Note:*** You will see that the response is missing associated information that was on the API Page. The *title* and *description* are no longer able to be pulled from AEM, and must now either be hard-coded or extracted from another source. Keep this in mind when using the Assets REST API.

#### 2. FAQ Assets HTTP API JSON

Now, look at the FAQ list of displayed using the Assets REST API.

<p align="center">
  <img src="/images/faq-http-api-menu.png" width="300px" />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="/images/faq-http-api-app.png" width="300px" />
</p>


### Exercise 2.3 - FAQ API Page JSON Consumer

Now let's look at the difference in the code for the consuming application. Navigate to this file and open the PhoneGap code which consumes the JSON response:

` ~/Desktop/summit-L763/summit-L763-aem/app/src/pages/faq-cf-page/faq-cf-page.ts `

<p align="center">
  <img src="/images/faq-cf-page.png" />
</p>

You'll notice on lines 27-32, the application must navigate to the necessary properties in the JSON output in order to make them available for rendering.

Additionally on lines 34-39, the developer must create the list of items in the correct order. 

### Exercise 2.4 - Assets HTTP API JSON Consumer

Compare the previous consumer code against the mobile app using the Assets REST API.

<p align="center">
  <img src="/images/faq-http-api.png" />
</p>

As you can see, there is much less logic necessary. The consumer can pull in the entries directly off of the response.


### Exercise 2.5 - Editing order for FAQs

If you noticed, the order of the FAQs listed using the Assets HTTP API is not in the same order as the items using the API Page. Let's reorder them into the correct order. 


#### 1. Open the FAQ Assets folder:
  * [http://localhost:4502/assets.html/content/dam/summit-lab-763/faq-fragments]()

#### 2. Change to the *List View*, if are not already in it:

<p align="center">
  <img src="/images/faq-cf-list-view.png" />
</p>

#### 3. Rearrange the items into the correct order using the entry handles:

<p align="center">
  <img src="/images/faq-cf-reorder.png" />
</p> 

#### 4. Once the items are in the correct order, select all of the Content Fragments and publish them.

<p align="center">
  <img src="/images/faq-cf-publish.png" />
</p> 

#### 5. Now, if you refresh the Mobile app, you should see all of the FAQ items in the correct order.

<p align="center">
  <img src="/images/faq-cf-app-reordered.png" width="300px"/>
</p> 

### Exercise 2.6 - Add a new FAQ Entry

Since the entries are built off of the content in the Assets folder, adding a new one will make it immediately available on the mobile app.

#### 1. Create a new FAQ content fragment

* <p align="center">
 <img src="/images/faq-cf-new-1.png" />
</p> 

* <p align="center">
 <img src="/images/faq-cf-new-2.png" />
</p>

* <p align="center">
 <img src="/images/faq-cf-new-3.png" />
</p>

* <p align="center">
  <img src="/images/faq-cf-new-4.png" width="400px" />
</p>

#### 2. Fill in details and save the fragment

* <p align="center">
  <img src="/images/faq-cf-details.png" />
</p>

* <p align="center">
  <img src="/images/faq-cf-new.png" />
</p>

#### 3. Reorder the new FAQ Item

Reorder the FAQ Items, so the newest one is first.

<p align="center">
  <img src="/images/faq-cf-reorder-new.png" />
</p> 

#### 4. Publish the new FAQ Item

Select the new FAQ item, and publish it.

<p align="center">
  <img src="/images/faq-cf-publish-new.png" />
</p> 

#### 5. Preview the changes in the mobile app

Now the new FAQ item should be displayed first in the REST API list. You can also view the API Page version and see that it is not there, as it is not referenced on the API Page.

<p align="center">
  <img src="/images/faq-cf-app-new.png" width="300px" />
</p> 

### Next Lesson

Finally, let's look at how we can completely customize the JSON output for AEM Resources using Sling Model Exporters (SME).

[Start the next Lesson](lesson-3.md)
