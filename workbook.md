# Headless Architecture with Adobe Experience Manager

## Table of Contents

* Lab Overview
* Lesson 0 - Software Installation
* Lesson 1 - Content Fragments
* Lesson 2 - Assets HTTP API
* Lesson 3 - Sling Model Exporter
* Next Steps
* Appendix

## Lab Overview

In this lab we will explore the myriad ways Experience Manager can expose its content in a headless fashion. You will learn best practices, and get guidance on which approach is appropriate based on context.

### Key Takeaways

* Learn headless use cases for Content Fragments and Content Services
* Learn how to create Content Fragments from a Content Fragment Model.
* Learn the Assets REST APIs for Content & Experience Fragments
* Leverage features in Content & Experience Fragments, and Sling Model Exporter

### Prerequisites

* Adobe Experience Manager 6.5 M3
* Node.js
* PhoneGap
* Ionic
* Maven (v3.5)


## Lesson 0 - Software Installation & Overview

### Objective

1. Install the pre-requisite software.
1. Validate connectivity of Mobile App to AEM.
 
### Lesson Context

This lesson sets up the software for the remaining lessons. Most should already be installed and ready to execute; but validating its state is important to ensure remaining lessons are successful.

### Mobile Stack Overview
Our Mobile App is built using **Apache Cordova** technology and utilizes **Angular** for its JavaScript Framework and **Ionic** as the HTML5 UI Framework.

You can learn more about these technologies here:

 * Apache Cordova: https://cordova.apache.org 
 * Ionic Framework: https://ionicframework.com
 * Angular: https://angular.io 

A typical hybrid mobile app stack built on these technologies would look like the image below:
<span class="align-center">
    <span>
        ![](/images/mobile-stack.png)
    </span>
</span>

> ***Note:*** ***PhoneGap Build*** *is a cloud service for compiling PhoneGap applications. We will not be using it for the purposes of this lab.*

### Exercise 0.1 - Install Initial Code and Content

Execute these commands to ensure that the code and content package are installed.

1. Open the *Terminal* application

2. Change the working directory to the `summit-L763-aem` folder:

```
$ cd ~/Desktop/Summit-2019/summit-L763-aem
```

3. Install the AEM application code on both Author and Publish instances.

```
$ mvn -PautoInstallPackage clean install

$ mvn -PautoInstallPackagePublish clean install
```  

4. Change the working directory to the `packages` folder:

```
$ cd ~/Desktop/Summit-2019/packages
```

5. Install the provided content package on both Author and Publish instances. You can either install it through CRX Package
    Manager or the commands below:

```
$ mvn com.day.jcr.vault:content-package-maven-plugin:install -Dvault.file=./L763-Headless-Architecture-1.0.zip

$ mvn com.day.jcr.vault:content-package-maven-plugin:install -Dvault.file=./L763-Headless-Architecture-1.0.zip -Dvault.targetURL="http://localhost:4503/crx/packmgr/service.jsp" 
```  

### Exercise 0.2 - Validate Installed Software

Execute these commands to validate that the necessary software is installed.

1. Open the *Terminal* application.

2. Validate Node version.

  ```sh
  $ npm -v
  6.4.1
  ```

3. Validate PhoneGap

  ```sh
  $ phonegap -v
  8.2.2
  ```

4. Validate Ionic

  ```
  $ ionic --version
  4.7.1
  ```

### Exercise 0.3 - Preview the Mobile App

Follow these steps to run Ionic and preview the mobile app in your browser.

1. Change to the mobile app directory

```
$ cd ~/Desktop/Summit-2019/app
```

2. Run the Ionic Preview Command


```
$ ionic serve --lab
```

3. Preview the Mobile App

Launching the lab should automatically open the browser to the Mobile preview. If it does not, use the bookmark *Headless Lab Ionic Preview*.

> ***Note:*** You can pick either ***iPhone***,  ***Android*** or ***Windows*** in the platforms dropdown on the top right of this page and view the app in that flavor. The Ionic Framework automatically attaches the appropriate icons & styling to suit the specific platform.

You should now see the mobile app preview with five menu items as shown below:
<p align="center">
  <img src="/images/app-home.png" width="300px" />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="/images/menu-items.png" width="300px" />
</p>


## Lesson 1 - Content Fragments via API Pages

### Lesson Context

This lesson covers headless delivery of content fragments built using the **Conference Speaker Content Fragment Model (CFM)**. 

### Definition

***Content Fragment Model (CFM)*** - Content Fragment Models define the structure of content for your content fragments. In AEM 6.5, a content fragment model now allows us to add additional elements to make up a bigger piece of content than just pure text. This helps in creating "content objects" that can then be exposed via Content Services for cross-channel consumption.

### Scenario

Our Mobile App needs to display a list of conference speakers as shown below in the UX handed to us by our design team.

<p align="center">
  <img src="/images/speakers-final.png" width="300px" />
</p>

Let's preview the Summit Speakers menu item in the mobile app. Compare this with what you see in the mobile app preview.

### Exercise 1.1 - Look at the content exposed from AEM
Let's take a look at the content in AEM and see how it is built out. We will work our way back from the JSON endpoint that the mobile app points to in AEM and look at how that is built out.

#### 1. Summit Speakers JSON API Endpoint
This can be found in Line 18 of config.ts located in the following location from the project code root path: 
  * ` app/src/providers/config.ts ` 

Let's follow this URL and take a look at the JSON on the AEM Publish server
  * [http://localhost:4503/content/we-retail/us/en/api/speakers.model.json]()

> ***Pro Tip:*** Adding 'tidy' to the url will make the JSON easier to read if you don't already have browser add-ons to do that for you:
> [http://localhost:4503/content/we-retail/us/en/api/speakers.model.tidy.json]()

Note that the following pieces of information are being exposed in the JSON:

* Title
* Text
* Multiple Content Fragments with the following elements:
    * Speaker Name
    * Speaker Byline
    * Speaker Image
    * Speaker Date of Birth

> ***Note:*** Even before Content Services existed, AEM was able to expose any content as JSON just by using the default .json extension. (Sling JSON Servlet) exposes a “raw” representation of the node, which can change over time / versions of AEM. In effect, the Sling JSON Servlet is effectively exposing the implementation details, and not necessarily a “stable” JSON API that can be consumed by other applications. Take a look for yourself at the difference between .json and .model.json for this particular page.
> [http://localhost:4503/content/we-retail/us/en/api/speakers.4.json]()
 
#### 2. Preview the Summit Speakers HTML page on AEM Publish using the following URL
Let's look at the actual AEM page that exposes this JSON
 * [http://localhost:4503/content/we-retail/us/en/api/speakers.html]()

> ***Note:*** Just by changing ".html" to ".model.json", we were able to expose all this information in the page in a well-structured JSON that our mobile app (or for that matter, any 3rd party application) can consume. Because we used Core Components on this page, there was no need to build any custom code to make this happen. A business user now has the ability to update this page and change content in the mobile app without writing a single line of code!


#### 3. Open the same page in AEM Author using the following URL (in a new tab)
  * [http://localhost:4502/editor.html/content/we-retail/us/en/api/speakers.html]()

### Exercise 1.2 - Title Mania!

Let's update the title of this screen in the app. To do this, we need to perform the following steps:

* **Step 1:** Edit the Speakers page
* **Step 2:** Update the title of the page
* **Step 3:** Publish your changes
* **Step 4:** Preview the changes in the mobile app

#### 1. Edit the Speakers page
To make any changes, you need to first be in Edit Mode in the Authoring interface

<p align="center">
  <img src="/images/edit-mode.png" />
</p>

#### 2. Update the title of the page
Edit the Title component and update the value with something new.

<p align="center">
  <img src="/images/edit-title.png" />
</p>

#### 3. Publish your changes
From the top-left menu, publish your changes by clicking on "Publish Page" menu option as shown below:

<p align="center">
  <img src="/images/publish-speakers.png" width="300px"/>
</p>

> ***Pro Tip:*** Mobile or any 3rd party apps should point to content in the Publish Tier and not Author Tier. Typically, you would have a Dispatcher and a Content Delivery Network on top of the Publish server, but in this lab we are just using the Publish server. We can still leverage all authoring capabilities and workflow before content becomes published like we would for a normal website running on AEM.


#### 4. Preview your changes in the JSON & Mobile App

Look at the JSON and check if you can find your updated value
  * [http://localhost:4503/content/we-retail/us/en/api/speakers.model.json]()

    > ***Hint:*** You can search for your updated change in the JSON by using Command + F

Refresh the mobile app preview and navigate to the Summit Speakers menu item and take a look at your updated value there

<p align="center">
  <img src="/images/updated-title-app.png" width="400px" />
</p>


### Exercise 1.3 - Release the Images!
Looking at the app preview, we can see that the app is pulling in the following elements from content fragments in AEM:

* Speaker Name
* Speaker Byline
* Speaker Date of Birth

but it is not using

* Speaker Image

yet. 

Let's fix the experience and pull in this additional piece of content to the mobile app. To do this, we need to perform the following steps:

* **Step 1:** Edit the Summit Speakers App Screen
* **Step 2:** Add images to the display of the mobile app screen
* **Step 3:** Preview the changes in the mobile app

#### 1. Edit the Summit Speakers App Page
This is located in the following location from the project code root path:
  * ` app/src/pages/summit-speakers/summit-speakers.html ` 

#### 2. Add images to the display of the mobile app screen 
Uncomment Line 34. This line of code pulls in the right value from the Model JSON and adds it to the app screen.

<p align="center">
  <img src="/images/add-images.png" />
</p>

#### 3. Preview the Mobile App
Now you should see images show up in the mobile app when you navigate to the Summit Speakers Menu item as shown below:

<p align="center">
  <img src="/images/images-added-app.png" width="300px" />
</p>

### Exercise 1.4 - Cute.. but let's release the *Right* Images!
Despite our personal preference for these images, let's now update the content fragments with the right images of the actual speakers.

There are two ways you can get to the content fragments:

#### Option 1: Edit the Content Fragment from the Page directly**

* **Step 1:** Open the Summit Speakers AEM Page
* **Step 2:** Edit the Summit Speakers AEM Page
* **Step 3:** Update Content Fragments
* **Step 4:** Publish the changes
* **Step 5:** Preview the changes in the mobile app

#### 1. Open the Summit Speakers AEM Page
Navigate to: [http://localhost:4502/editor.html/content/we-retail/us/en/api/speakers.html]() 

#### 2. Edit the Summit Speakers AEM Page
To make any changes, you need to first be in Edit Mode in the Authoring interface:

<p align="center">
  <img src="/images/edit-mode.png" />
</p>

#### 3. Update Content Fragments

1. Click on each fragment and then click on the pencil icon as shown below. This will take you to the actual fragment to make the necessary edits.

<p align="center">
  <img src="/images/edit-cf.png" />
</p>


2. Update the Speaker Headshot by selecting the right image from the Digital Asset Manager (DAM) folder as shown below:

* <p align="center">
  <img src="/images/edit-speaker-headshot.png" />
</p>
* <p align="center">
  <img src="/images/select-speaker-headshot.png" />
</p>
    
> ***Note:*** When selecting the image, please click on the thumbnail of the image and then click on "Select" button located in the top right corner of the modal.

3. Save the updated content fragment

<p align="center">
  <img src="/images/save-cf.png" />
</p> 

4. Repeat this for all the fragments on the page and update all their images to the right one in the DAM folder.


#### 4. Publish your changes
From the top-left menu, publish your changes by clicking on "Publish Page" menu option as shown below:

<p align="center">
  <img src="/images/publish-speakers.png" width="400px" />
</p>

#### 5. Preview the Mobile App
Now you should see *right* images show up in the mobile app when you navigate to the Summit Speakers Menu item as shown below:

<p align="center">
  <img src="/images/speakers-final.png" width="300px">
</p>

### (Optional) Option 2: Edit the Content Fragment by browsing to it from the Assets Console in AEM
Alternately, you can browse to the folder in the Assets console and edit the content fragments directly there as shown below:

* **Step 1:** Browse to the Assets console
* **Step 2:** Select Files View
* **Step 3:** Select Column View
* **Step 4:** Browse to the folder with the speaker content fragments
* **Step 5:** Select a Content Fragment and Click on Edit
* **Step 6:** Repeat the same for all remaining content fragments
* **Step 7:** Publish all content fragments
* **Step 8:** Preview the changes in the mobile app

#### 1. Browse to the Assets console:

<p align="center">
  <img src="/images/assets-console-1.png" />
</p>

#### 2. Select Files View

<p align="center">
  <img src="/images/assets-console-2.png" />
</p>

#### 3. Select Column View
> ***Note:*** The fastest way around content in AEM is using column view. Select Column View in your view options as shown below:

<p align="center">
  <img src="/images/column-view.png" />
</p>


#### 4. Browse to the following path or use the following URL:
  * [http://localhost:4502/assets.html/content/dam/summit-lab-763/conference-speaker-fragments]()

#### 5. Select a Content Fragment and Click on Edit

<p align="center">
  <img src="/images/assets-console-3.png" />
</p>

#### 6. Repeat the previous steps for all the remaining content fragments

#### 7. Quick Publish the content fragments

<p align="center">
  <img src="/images/assets-console-publish.png"/>
</p>

#### 8. Preview the changes in the mobile app
Now you should see *right* images show up in the mobile app when you navigate to the Summit Speakers Menu item as shown below:

<p align="center">
  <img src="/images/speakers-final.png" width="300px">
</p>



### Exercise 2.5 - Everybody is a Summit Speaker!!
Have you ever wanted to be a Summit Speaker?? Today is your lucky day! This activity involves the following steps:

* **Step 1:** Create a new speaker content fragment
* **Step 2:** Fill in your details and save the fragment
* **Step 3:** Add it to the Speakers API page
* **Step 4:** Publish the page
* **Step 5:** Preview the changes in the mobile app


#### 1. Create a new speaker content fragment

1. Browse to the summit speakers content fragment folder in the Assets console:
  * [http://localhost:4502/assets.html/content/dam/summit-lab-763/conference-speaker-fragments]()

2. Create a new Speaker fragment based on the Speaker Content Fragment Model

* <p align="center">
  <img src="/images/cf-new-speaker-1.png" />
</p>

* <p align="center">
  <img src="/images/cf-new-speaker-2.png" />
</p>

* <p align="center">
  <img src="/images/cf-new-speaker-3.png" />
</p>

* <p align="center">
  <img src="/images/cf-new-speaker-4.png" width="400px" />
</p>

#### 2. Fill in your details and save the fragment

* <p align="center">
  <img src="/images/cf-speaker-details.png" />
</p>

* <p align="center">
  <img src="/images/cf-speakers-new.png" />
</p>

#### 3. Add it to the Speakers AEM page

1. Edit the Speakers AEM Page
  * [http://localhost:4502/editor.html/content/we-retail/us/en/api/speakers.html]()

2. Open the left rail and drag the content fragment component to the page below the Title & Text components and above the existing fragments.

<p align="center">
  <img src="/images/speakers-api-page-add-cf.png" />
</p>

3. Drag the new fragment you just created in the previous Step to the Content Fragment component you just dragged onto the page.

* <p align="center">
  <img src="/images/speakers-api-page-find-cf.png" />
</p>

* <p align="center">
  <img src="/images/speakers-api-page-drag-cf.png" />
</p>


#### 4. Publish the page
From the top-left menu, publish your changes by clicking on "Publish Page" menu option as shown below:

* <p align="center">
  <img src="/images/publish-speakers.png" width="400px"/>
</p>

* <p align="center">
  <img src="/images/publish-speakers-ref.png" />
</p>

#### 5. Preview the changes in the mobile app

Now when you preview the changes in the mobile app, you should see the latest speaker show up on top.

<p align="center">
  <img src="/images/app-speakers-new-cf.png" width="300px"/>
</p>


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


## Next Steps

Look for new HelpX and Tutorials on updates to the AEM Assets REST API!

## Appendix

### References

#### Sling Model Exporter

**Overview:**
* [https://helpx.adobe.com/experience-manager/kt/platform-repository/using/sling-model-exporter-tutorial-understand.html]()

**Develop:**
* [https://helpx.adobe.com/experience-manager/kt/platform-repository/using/sling-model-exporter-tutorial-develop.html]()

#### AEM Content Services
* [https://helpx.adobe.com/experience-manager/kt/sites/using/content-services-tutorial-use.html]()

#### Content Fragment Models
* [https://helpx.adobe.com/experience-manager/6-4/assets/using/content-fragments-models.html]()

#### AEM Assets REST API
* [https://helpx.adobe.com/experience-manager/6-4/assets/using/mac-api-assets.html]()

#### Enabling JSON Export for a component 
* [https://helpx.adobe.com/experience-manager/kt/platform-repository/using/sling-model-exporter-tutorial-develop.html]()
* [https://helpx.adobe.com/experience-manager/6-3/release-notes/json-exporter-dev-fp.html]()

#### AEM Assets REST API
* [https://helpx.adobe.com/experience-manager/6-4/assets/using/mac-api-assets.html]()
