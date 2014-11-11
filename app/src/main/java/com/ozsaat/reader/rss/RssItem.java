package com.ozsaat.reader.rss;

import android.os.Parcel;
import android.os.Parcelable;

public class RssItem implements Parcelable {

    public static final Parcelable.Creator<RssItem> CREATOR
            = new Parcelable.Creator<RssItem>() {
        public RssItem createFromParcel(Parcel in) {
            return new RssItem(in);
        }

        public RssItem[] newArray(int size) {
            return new RssItem[size];
        }
    };
    private long id;
    private String title;
    private String link;
    private String date;
    private String author;
    private String thumbnail;

    public RssItem(String title, String date, String author, String thumbnail) {
        this.title = title;
        this.date = date;
        this.author = author;
        this.thumbnail = thumbnail;
    }

    public RssItem() {

    }

    private RssItem(Parcel in) {
        this.title = in.readString();
        this.author = in.readString();
        this.date = in.readString();
        this.thumbnail = in.readString();

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(date);
        dest.writeString(thumbnail);

    }

}
