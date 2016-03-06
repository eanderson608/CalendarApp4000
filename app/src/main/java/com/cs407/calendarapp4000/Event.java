package com.cs407.calendarapp4000;

public class Event {

    private String _id;
    private String title;
    private String description;
    private String shortDate;
    private String longDate;
    private String __v;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDate() {
        return shortDate;
    }

    public void setShortDate(String shortDate) {
        this.shortDate = shortDate;
    }

    public String getLongDate() {
        return longDate;
    }

    public void setLongDate(String longDate) {
        this.longDate = longDate;
    }

    @Override
    public String toString() {
        return "{" +
                "_id='" + _id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", shortDate='" + shortDate + '\'' +
                ", longDate='" + longDate + '\'' +
                ", __v='" + __v + '\'' +
                '}';
    }
}
