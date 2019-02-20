import { NgModule } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { IonicPageModule } from 'ionic-angular';

import { FAQPageHttpAPI } from './faq-http-api';

@NgModule({
    declarations: [
        FAQPageHttpAPI,
    ],
    imports: [
        IonicPageModule.forChild(FAQPageHttpAPI),
        TranslateModule.forChild()
    ],
    exports: [
        FAQPageHttpAPI
    ]
})
export class FAQPageHttpAPIModule { }
