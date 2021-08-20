package com.example.pawsome;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Breeds implements Parcelable {

        private Weight weight;
        private Height height;
        private int id;
        private String name;
        private String bred_for;
        private String breed_group;
        private String life_span;
        private String temperament;
        private String reference_image_id;

        public Breeds(Weight weight, Height height, int id, String name, String bred_for, String breed_group, String life_span, String temperament, String reference_image_id) {
            this.weight = weight;
            this.height = height;
            this.id = id;
            this.name = name;
            this.bred_for = bred_for;
            this.breed_group = breed_group;
            this.life_span = life_span;
            this.temperament = temperament;
            this.reference_image_id = reference_image_id;
        }

    protected Breeds(Parcel in) {
        weight = in.readParcelable(Weight.class.getClassLoader());
        height = in.readParcelable(Height.class.getClassLoader());
        id = in.readInt();
        name = in.readString();
        bred_for = in.readString();
        breed_group = in.readString();
        life_span = in.readString();
        temperament = in.readString();
        reference_image_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(weight, flags);
        dest.writeParcelable(height, flags);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(bred_for);
        dest.writeString(breed_group);
        dest.writeString(life_span);
        dest.writeString(temperament);
        dest.writeString(reference_image_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Breeds> CREATOR = new Creator<Breeds>() {
        @Override
        public Breeds createFromParcel(Parcel in) {
            return new Breeds(in);
        }

        @Override
        public Breeds[] newArray(int size) {
            return new Breeds[size];
        }
    };

    public Weight getWeight() {
            return weight;
        }

        public Height getHeight() {
            return height;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getBred_for() {
            return bred_for;
        }

        public String getBreed_group() {
            return breed_group;
        }

        public String getLife_span() {
            return life_span;
        }

        public String getTemperament() {
            return temperament;
        }

        public String getReference_image_id() {
            return reference_image_id;
        }







}
