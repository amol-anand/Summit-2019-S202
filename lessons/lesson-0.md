# Headless Architecture with Adobe Experience Manager

## Table of Contents

* [Lab Overview](/README.md#lab-overview)
* [Lesson 0 - Software Installation](lesson-0.md)
* [Lesson 1 - Content Fragments](lesson-1.md)
* [Lesson 2 - Assets HTTP API](lesson-2.md)
* [Lesson 3 - Sling Model Exporter](lesson-3.md)
* [Next Steps](#next-steps)
* [Appendix](Appendix.)


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

### Next Lesson

At this point you are ready to start working on the exercises for the lab. As changes are made in the Mobile App, the preview will reload automatically.

[Start the next Lesson.](lesson-1.md)
