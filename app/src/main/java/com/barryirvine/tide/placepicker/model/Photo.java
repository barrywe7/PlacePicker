package com.barryirvine.tide.placepicker.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Photo implements Parcelable {
    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        @Override
        public Photo createFromParcel(final Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(final int size) {
            return new Photo[size];
        }
    };
    private String photoReference;

    public Photo() {
    }

    protected Photo(final Parcel in) {
        this.photoReference = in.readString();
    }

    public String getPhotoReference() {
        return photoReference;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(this.photoReference);
    }
}
