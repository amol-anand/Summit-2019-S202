import { NgModule } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { IonicPageModule } from 'ionic-angular';

import { FAQPageCFPage } from './faq-cf-page';

@NgModule({
    declarations: [
        FAQPageCFPage,
    ],
    imports: [
        IonicPageModule.forChild(FAQPageCFPage),
        TranslateModule.forChild()
    ],
    exports: [
        FAQPageCFPage
    ]
})
export class FAQPageCFPageModule { }
