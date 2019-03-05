import { Component, Input, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { MapTo } from '@adobe/cq-angular-editable-components';
import { ThrowStmt } from '@angular/compiler';

@Component({
    selector: 'app-speakerlist',
    templateUrl: './speakerlist.component.html',
    styleUrls: ['./speakerlist.component.scss']
})
export class SpeakerlistComponent implements OnInit {

    @Input() jsonPath: string;
    @Input() orderBy: string;
    @Input() sortOrder: string;
    @Input() maxItems: string;

    // private baseURL: string = "";
    private speakers: [any];

    constructor(private sanitizer: DomSanitizer, private httpClient: HttpClient) {
    }

    getSpeakers() {
        this.httpClient.get(this.jsonPath).subscribe(res => {
            this.speakers = res["entities"].map(item => {
                return new Speaker(item);
            });
            if (this.speakers && this.speakers.length > 0) {
                if (this.orderBy) {
                    var sortDir = 1;
                    if (this.sortOrder && this.sortOrder == 'desc'){
                        sortDir = -1;
                    }
                    this.speakers.sort((a, b) => ((this.orderBy == 'speaker-title' ? ((a.speakerName > b.speakerName) ? 1 : -1) : ((a.speakerDOB > b.speakerDOB) ? 1 : -1)) * sortDir));
                }
                if (this.maxItems && this.maxItems != '0') {
                    var size = parseInt(this.maxItems);
                    while (this.speakers.length > size) {
                        this.speakers.pop();
                    }
                }
            }
            console.log(res);
        },
            (err: HttpErrorResponse) => {
                if (err.error instanceof Error) {
                    console.log("Client-side error occured.: " + err);
                } else {
                    console.log("Server-side error occured: " + err);
                }
            });
    }

    ngOnInit() {
        console.log("Assets HTTP API URL to get speakers from: " + this.jsonPath);
        this.getSpeakers();
    }

}

class Speaker {

    speakerName: string;
    speakerByline: string;
    speakerImage: string;
    speakerDOB: Date;

    constructor(data) {
        this.speakerByline = data.properties.elements.speakerByline.value;
        this.speakerDOB = data.properties.elements.speakerDOB.value;
        this.speakerImage = data.properties.elements.speakerImage.value;
        this.speakerName = data.properties.elements.speakerName.value;

    }

}

const SpeakerListEditConfig = {
    emptyLabel: 'Speaker List',

    isEmpty: function (componentData) {
        return !componentData || !componentData.jsonPath;
    }
};

MapTo('wknd-events/components/content/speakerlist')(SpeakerlistComponent, SpeakerListEditConfig);
