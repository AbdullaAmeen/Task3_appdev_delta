package com.example.pawsome;

import android.os.Parcel;
import android.os.Parcelable;

public class Height implements Parcelable {
    private String imperial;
    private String metric;

    protected Height(Parcel in) {
        imperial = in.readString();
        metric = in.readString();
    }

    public static final Creator<Height> CREATOR = new Creator<Height>() {
        @Override
        public Height createFromParcel(Parcel in) {
            return new Height(in);
        }

        @Override
        public Height[] newArray(int size) {
            return new Height[size];
        }
    };

    public String getImperial() {
        return imperial;
    }

    public String getMetric() {
        return metric;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imperial);
        dest.writeString(metric);
    }
}