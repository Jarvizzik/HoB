package com.example.user.kit;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
    //посилання на картинку івента
    private String eventImage;
    //назва івенту
    private String eventName;
    //дата івенту
    private String eventDate;
    //місце проведення івенту
    private String eventLocation;
    //оплата івенту
    private String eventPrice;
    //контент
    private String eventContent;
    //деталі
    private String eventMore;
    //посилання на сторінку івента
    private String eventPage;

    public Event(String eventImage, String eventName, String eventDate,String eventLocation, String eventPrice, String eventContent, String eventMore, String eventPage) {
        this.eventImage = eventImage;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventPrice = eventPrice;
        this.eventContent = eventContent;
        this.eventMore = eventMore;
        this.eventPage = eventPage;
    }

    protected Event(Parcel in) {
        String[] data = new String[8];
        in.readStringArray(data);
        eventImage = data[0];
        eventName = data[1];
        eventDate = data[2];
        eventLocation = data[3];
        eventPrice = data[4];
        eventContent = data[5];
        eventMore = data[6];
        eventPage = data[7];
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel parcel) {
            return new Event(parcel);
        }

        @Override
        public Event[] newArray(int i) {
            return new Event[i];
        }
    };

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(String eventPrice) {
        this.eventPrice = eventPrice;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    public String getEventMore() {
        return eventMore;
    }

    public void setEventMore(String eventMore) {
        this.eventMore = eventMore;
    }

    public String getEventPage() {
        return eventPage;
    }

    public void setEventPage(String eventPage) {
        this.eventPage = eventPage;
    }

    @Override
    public int describeContents() {
            return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {eventImage, eventName, eventDate,eventLocation, eventPrice, eventContent, eventMore, eventPage});

    }
}
