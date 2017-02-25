package com.biz.entry;

import com.shsunframework.entry.BaseEntry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shsun on 17/1/18.
 */

public class PersonEntry extends BaseEntry {

    private final String mName;
    private final String mDescription;
    private final String mPrice;
    private final String mCategory;
    private final String mImageUrl;

    public PersonEntry(String id, String name, String description, String price, String category,
                       String imageUrl) {
        super(id);
        this.mName = name;
        this.mDescription = description;
        this.mPrice = price;
        this.mCategory = category;
        this.mImageUrl = imageUrl;
    }

    private PersonEntry(Parcel in) {
        super(in);
        mName = in.readString();
        mDescription = in.readString();
        mPrice = in.readString();
        mCategory = in.readString();
        mImageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(mName);
        out.writeString(mDescription);
        out.writeString(mPrice);
        out.writeString(mCategory);
        out.writeString(mImageUrl);
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public static final Parcelable.Creator<PersonEntry> CREATOR =
            new Parcelable.Creator<PersonEntry>() {
                @Override
                public PersonEntry createFromParcel(Parcel in) {
                    return new PersonEntry(in);
                }

                @Override
                public PersonEntry[] newArray(int size) {
                    return new PersonEntry[size];
                }
            };

}
