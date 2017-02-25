package com.biz.entry;

import com.shsunframework.entry.BaseEntry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shsun on 17/2/25.
 */

public class VideoEntry extends BaseEntry {

    private String mName;
    private String mUrl;

    public VideoEntry(String id, String name, String url) {
        super(id);
        this.mName = name;
        this.mUrl = url;
    }

    private VideoEntry(Parcel in) {
        super(in);
        mName = in.readString();
        mUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(mName);
        out.writeString(mUrl);
    }

    public String getName() {
        return mName;
    }

    public String getUrl() {
        return mUrl;
    }

    public static final Parcelable.Creator<VideoEntry> CREATOR =
            new Parcelable.Creator<VideoEntry>() {
                @Override
                public VideoEntry createFromParcel(Parcel in) {
                    return new VideoEntry(in);
                }

                @Override
                public VideoEntry[] newArray(int size) {
                    return new VideoEntry[size];
                }
            };

}
