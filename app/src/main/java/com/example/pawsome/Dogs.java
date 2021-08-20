package com.example.pawsome;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.HeaderViewListAdapter;

import java.io.Serializable;
import java.util.ArrayList;

public class Dogs implements Parcelable {


    private ArrayList<Breeds> breeds;
    private String id;
    private String url;
    private int width;
    private int height;

    public Dogs(Breeds breeds, String id, String url, int width, int height) {
        this.breeds = new ArrayList<>();
        this.id = id;
        this.url = url;
        this.width = width;
        this.height = height;
    }

    protected Dogs(Parcel in) {
        breeds = in.createTypedArrayList(Breeds.CREATOR);
        id = in.readString();
        url = in.readString();
        width = in.readInt();
        height = in.readInt();
    }

    public static final Creator<Dogs> CREATOR = new Creator<Dogs>() {
        @Override
        public Dogs createFromParcel(Parcel in) {
            return new Dogs(in);
        }

        @Override
        public Dogs[] newArray(int size) {
            return new Dogs[size];
        }
    };

    public ArrayList<Breeds> getBreeds() {
        return breeds;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(breeds);
        dest.writeString(id);
        dest.writeString(url);
        dest.writeInt(width);
        dest.writeInt(height);
    }
}
