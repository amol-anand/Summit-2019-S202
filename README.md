
# Summit 2019 Session S202

Instructions to build:


Build to AEM Author:
1. `cd summit-L763-aem`
2. `mvn -PautoInstallPackage clean install `
3. `cd wknd-events`
4. `mvn -PautoInstallPackage clean install `
5. Install packages from 'packages' folder

Build to AEM Publish:
1. `cd summit-L763-aem`
2. `mvn -PautoInstallPackagePublish clean install `
3. `cd wknd-events`
4. `mvn -PautoInstallPackagePublish clean install `
5. Install packages from 'packages' folder

Run Ionic Mobile app:
1. `cd app`
2. `ionic serve --lab`

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
