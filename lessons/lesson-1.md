# Headless Architecture with Adobe Experience Manager

## Table of Contents

* [Lab Overview](/README.md#lab-overview)
* [Lesson 0 - Software Installation](/lessons/lesson-0.md)
* [Lesson 1 - Content Fragments](/lessons/lesson-1.md)
* [Lesson 2 - Assets HTTP API](/lessons/lesson-2.md)
* [Lesson 3 - Sling Model Exporter](/lessons/lesson-3.md)
* [Next Steps](/README.md#next-steps)
* [Appendix](/README.md#Appendix)


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

### Next Lesson

Now let's take a look at how to use the new AEM Assets REST API for content fragments.

[Start the next Lesson](lesson-2.md)
