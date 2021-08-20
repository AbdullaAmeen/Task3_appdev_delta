package com.example.pawsome;

import android.os.Parcel;
import android.os.Parcelable;

public class Weight implements Parcelable {
    private String imperial;
    private String metric;

    protected Weight(Parcel in) {
        imperial = in.readString();
        metric = in.readString();
    }

    public static final Creator<Weight> CREATOR = new Creator<Weight>() {
        @Override
        public Weight createFromParcel(Parcel in) {
            return new Weight(in);
        }

        @Override
        public Weight[] newArray(int size) {
            return new Weight[size];
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