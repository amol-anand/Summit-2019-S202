import { Component, Input, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { MapTo } from '@adobe/cq-angular-editable-components';

@Component({
    selector: 'app-eventfragment',
    templateUrl: './eventfragment.component.html',
    styleUrls: ['./eventfragment.component.scss']
})
export class EventfragmentComponent implements OnInit {

    @Input() fragmentContent: {};

    constructor() { }

    get event(): Event {
        return new Event(this.fragmentContent);
    }

    ngOnInit() { }

}

class Event {

    private monthNames: string[] = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

    title: string;
    description: string;
    imageUrl: string;
    eventCity: string;
    eventAddress: string[];
    eventType: string;
    eventDate: Date;
    eventTimes: string[];
    venueName: string;
    venuePage: string;
    ticketPrice: number;
    artistName: string;
    artistBio: string;

    constructor(data) {
        this.title = data.wkndEventTitle;
        this.description = data.wkndEventDescription;
        this.imageUrl = data.wkndEventImage;
        this.eventAddress = data.wkndEventAddress;
        this.eventCity = data.wkndEventCity;
        this.eventType = data.wkndEventType;
        this.eventDate = new Date(data.wkndEventDate);
        this.eventTimes = data.wkndEventTimes;
        this.venueName = data.wkndVenueName;
        this.venuePage = data.wkndVenuePage;
        this.ticketPrice = data.wkndTicketPrice;
        this.artistName = data.wkndArtistName;
        this.artistBio = data.wkndArtistBio;
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

const EventFragmentEditConfig = {
    emptyLabel: 'Event Fragment',

    isEmpty: function (componentData) {
        return !componentData || !componentData.fragmentContent;
    }
};

MapTo('wknd-events/components/content/eventfragment')(EventfragmentComponent, EventFragmentEditConfig);