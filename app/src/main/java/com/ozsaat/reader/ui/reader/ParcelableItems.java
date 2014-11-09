package com.ozsaat.reader.ui.reader;


import android.os.Parcel;
import android.os.Parcelable;

import com.ozsaat.reader.rss.RssItem;

import java.util.List;

public class ParcelableItems implements Parcelable {
    public static final Parcelable.Creator<ParcelableItems> CREATOR
            = new Parcelable.Creator<ParcelableItems>() {
        public ParcelableItems createFromParcel(Parcel in) {
            return new ParcelableItems(in);
        }

        public ParcelableItems[] newArray(int size) {
            return new ParcelableItems[size];
        }
    };
    List<RssItem> rssItems;


    public ParcelableItems(List<RssItem> rssItems) {
        this.rssItems = rssItems;
    }

    private ParcelableItems(Parcel in) {
        in.readList(rssItems, ClassLoader.getSystemClassLoader());

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(rssItems);

    }

}


