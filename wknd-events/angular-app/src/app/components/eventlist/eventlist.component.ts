import { Component, Input, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { MapTo } from '@adobe/cq-angular-editable-components';
import { ThrowStmt } from '@angular/compiler';

@Component({
    selector: 'app-eventlist',
    templateUrl: './eventlist.component.html',
    styleUrls: ['./eventlist.component.scss']
})
export class EventlistComponent implements OnInit {

    @Input() jsonPath: string;
    @Input() orderBy: string;
    @Input() sortOrder: string;
    @Input() maxItems: string;

    // private baseURL: string = "";
    private events: [any];

    constructor(private sanitizer: DomSanitizer, private httpClient: HttpClient) {
    }

    getEvents() {
        this.httpClient.get(this.jsonPath).subscribe(res => {
            this.events = res["entities"].map(item => {
                return new Event(item);
            });
            if (this.events && this.events.length > 0) {
                if (this.orderBy) {
                    var sortDir = 1;
                    if (this.sortOrder && this.sortOrder == 'desc'){
                        sortDir = -1;
                    }
                    this.events.sort((a, b) => ((this.orderBy == 'event-title' ? ((a.title > b.title) ? 1 : -1) : ((a.eventDate > b.eventDate) ? 1 : -1)) * sortDir));
                }
                if (this.maxItems && this.maxItems != '0') {
                    var size = parseInt(this.maxItems);
                    while (this.events.length > size) {
                        this.events.pop();
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
        console.log("Assets HTTP API URL to get events from: " + this.jsonPath);
        this.getEvents();
    }

}

class Event {

    private monthNames: string[] = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

    title: string;
    description: string;
    imageUrl: string;
    eventCity: string;
    eventAddress: string;
    eventType: string;
    eventDate: Date;
    eventTimes: string;
    venueName: string;
    venuePage: string;
    ticketPrice: string;
    artistName: string;
    artistBio: string;

    constructor(data) {
        this.title = data.properties.elements.wkndEventTitle.value;
        this.description = data.properties.elements.wkndEventDescription.value;
        this.imageUrl = data.properties.elements.wkndEventImage.value;
        this.eventAddress = data.properties.elements.wkndEventAddress.value;
        this.eventCity = data.properties.elements.wkndEventCity.value;
        this.eventType = data.properties.elements.wkndEventType.value;
        this.eventDate = new Date(data.properties.elements.wkndEventDate.value);
        this.eventTimes = data.properties.elements.wkndEventTimes.value;
        this.venueName = data.properties.elements.wkndVenueName.value;
        this.venuePage = data.properties.elements.wkndVenuePage.value;
        this.ticketPrice = data.properties.elements.wkndTicketPrice.value;
        this.artistName = data.properties.elements.wkndArtistName.value;
        this.artistBio = data.properties.elements.wkndArtistBio.value;
    }

    public get month(): string {
        return this.monthNames[this.eventDate.getMonth()];
    }

    public get day(): string {
        let tmp = this.eventDate.getDate().toString();

        if (tmp.length === 1) {
            tmp = '0' + tmp;
        }
        
        return tmp;
    }

    public get location(): string {
        let tmp = this.eventCity;
        
        if (this.venueName) {
            tmp = tmp + ' @ ' + this.venueName;
        }

        return tmp;
    }

}

const EventListEditConfig = {
    emptyLabel: 'Event List',

    isEmpty: function (componentData) {
        return !componentData || !componentData.jsonPath;
    }
};

MapTo('wknd-events/components/content/eventlist')(EventlistComponent, EventListEditConfig);
