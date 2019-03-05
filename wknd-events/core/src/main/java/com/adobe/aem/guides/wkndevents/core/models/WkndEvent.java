package com.adobe.aem.guides.wkndevents.core.models;

import java.util.GregorianCalendar;

public class WkndEvent {

    private static final String[] MONTHS_STRINGS = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

    private String wkndArtistName;
    private String[] wkndEventAddress;
    private String wkndEventCity;
    private GregorianCalendar wkndEventDate;

    private String wkndEventDescription;
    private String wkndEventImage;
    private String[] wkndEventTimes;
    private String wkndEventTitle;

    private String[] wkndEventType;
    private Double wkndTicketPrice;
    private String wkndVenueName;

    public WkndEvent(String wkndArtistName, String[] wkndEventAddress, String wkndEventCity, GregorianCalendar wkndEventDate,
                     String wkndEventDescription, String wkndEventImage, String[] wkndEventTimes, String wkndEventTitle,
                     String[] wkndEventType, Double wkndTicketPrice, String wkndVenueName){
        setWkndArtistName(wkndArtistName);
        setWkndEventAddress(wkndEventAddress);
        setWkndEventCity(wkndEventCity);
        setWkndEventDate(wkndEventDate);
        setWkndEventDescription(wkndEventDescription);
        setWkndEventImage(wkndEventImage);
        setWkndEventTimes(wkndEventTimes);
        setWkndEventTitle(wkndEventTitle);
        setWkndEventType(wkndEventType);
        setWkndEventAddress(wkndEventAddress);
        setWkndTicketPrice(wkndTicketPrice);
        setWkndVenueName(wkndVenueName);
    }

    public String getWkndEventDateMonth() {
        if (wkndEventDate != null) {
            return MONTHS_STRINGS[wkndEventDate.get(GregorianCalendar.MONTH)];
        }
        return "";
    }

    public String getWkndEventDateDay() {
        if (wkndEventDate != null) {
            return Integer.toString(wkndEventDate.get(GregorianCalendar.DAY_OF_MONTH));
        }
        return "";
    }

    public String getWkndArtistName() {
        return wkndArtistName;
    }

    public void setWkndArtistName(String wkndArtistName) {
        this.wkndArtistName = wkndArtistName;
    }

    public String[] getWkndEventAddress() {
        return wkndEventAddress;
    }

    public void setWkndEventAddress(String[] wkndEventAddress) {
        this.wkndEventAddress = wkndEventAddress;
    }

    public String getWkndEventCity() {
        return wkndEventCity;
    }

    public void setWkndEventCity(String wkndEventCity) {
        this.wkndEventCity = wkndEventCity;
    }

    public GregorianCalendar getWkndEventDate() {
        return wkndEventDate;
    }

    public void setWkndEventDate(GregorianCalendar wkndEventDate) {
        this.wkndEventDate = wkndEventDate;
    }

    public String getWkndEventDescription() {
        return wkndEventDescription;
    }

    public void setWkndEventDescription(String wkndEventDescription) {
        this.wkndEventDescription = wkndEventDescription;
    }

    public String getWkndEventImage() {
        return wkndEventImage;
    }

    public void setWkndEventImage(String wkndEventImage) {
        this.wkndEventImage = wkndEventImage;
    }

    public String[] getWkndEventTimes() {
        return wkndEventTimes;
    }

    public void setWkndEventTimes(String[] wkndEventTimes) {
        this.wkndEventTimes = wkndEventTimes;
    }

    public String getWkndEventTitle() {
        return wkndEventTitle;
    }

    public void setWkndEventTitle(String wkndEventTitle) {
        this.wkndEventTitle = wkndEventTitle;
    }

    public String[] getWkndEventType() {
        return wkndEventType;
    }

    public void setWkndEventType(String[] wkndEventType) {
        this.wkndEventType = wkndEventType;
    }

    public Double getWkndTicketPrice() {
        return wkndTicketPrice;
    }

    public void setWkndTicketPrice(Double wkndTicketPrice) {
        this.wkndTicketPrice = wkndTicketPrice;
    }

    public String getWkndVenueName() {
        return wkndVenueName;
    }

    public void setWkndVenueName(String wkndVenueName) {
        this.wkndVenueName = wkndVenueName;
    }

}