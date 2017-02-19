package com.biz.entry;

import android.os.Parcel;
import android.os.Parcelable;

import com.shsunframework.entry.BaseEntry;

/**
 * Created by shsun on 17/2/19.
 */

public class ChannelEntry extends BaseEntry {

    private final String name;

    public ChannelEntry(String id, String name) {
        super(id);
        this.name = name;
    }

    private ChannelEntry(Parcel in) {
        super(in);
        name = in.readString();
    }

    public String getName() {
        return name;
    }


    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(name);
    }

    public static final Parcelable.Creator<ChannelEntry> CREATOR =
            new Parcelable.Creator<ChannelEntry>() {
                @Override
                public ChannelEntry createFromParcel(Parcel in) {
                    return new ChannelEntry(in);
                }

                @Override
                public ChannelEntry[] newArray(int size) {
                    return new ChannelEntry[size];
                }
            };


}
