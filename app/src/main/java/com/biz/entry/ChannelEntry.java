package com.biz.entry;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.entry.XBaseEntry;

/**
 * Created by shsun on 17/2/19.
 */

public class ChannelEntry extends XBaseEntry {

    private final String mName;

    public ChannelEntry(String id, String name) {
        super(id);
        this.mName = name;
    }

    private ChannelEntry(Parcel in) {
        super(in);
        mName = in.readString();
    }

    public String getName() {
        return mName;
    }


    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(mName);
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
