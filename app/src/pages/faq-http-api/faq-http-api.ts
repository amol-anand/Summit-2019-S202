import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import {IonicPage, MenuController, NavParams, NavController} from 'ionic-angular';
import {FAQHttpAPI} from "../../providers/faq-http-api/faq-http-api";

@IonicPage()
@Component({
    selector: 'page-faq-http-api',
    templateUrl: 'faq-http-api.html'
})
export class FAQPageHttpAPI {

    public faqs: Array<{speaker: any}> = [];
    // private items: any;
    // public title: string;
    // public text: string;
    // public itemsOrder: any;
    private shownGroup: any;

    constructor(public menuCtrl: MenuController, public navParams: NavParams, public navCtrl: NavController, public translateService: TranslateService, private faqService: FAQHttpAPI) {
        console.log("In FAQ HTTP API constructor");
        this.faqService.getFAQs().map((res)=>res.json()).subscribe((res)=>{
            console.log("Items: "+JSON.stringify(res));

            // The order is managed by the folder.
            // this.itemsOrder = res[":items"]["root"][":itemsOrder"];

            // The items are entries in an array. Each object represents an FAQ entry.
            this.faqs = res["entities"];

            // The title doesn't exist.
            // this.title = this.items["title"]["text"];

            // The description on the page doesn't exist.
            // this.text = this.items["text"]["text"];

            // Assume everything in this folder is an FAQ
        }, (err)=>{
            console.error("Error getting Speakers: "+err.message);
        });
    }

    toggleGroup(group) {
        if (this.isGroupShown(group)) {
            this.shownGroup = null;
        } else {
            this.shownGroup = group;
        }
    }

    isGroupShown(group) {
        return this.shownGroup === group;
    }

}
