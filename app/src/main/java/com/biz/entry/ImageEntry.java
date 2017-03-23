package com.biz.entry;

import android.os.Parcel;
import android.os.Parcelable;

import com.shsunframework.entry.BaseEntry;

/**
 * Created by shsun on 23/3/2017.
 */

public class ImageEntry extends BaseEntry {

    private final String mUrl;
    private boolean mCanDisplay;

    public ImageEntry(String id, String url) {
        super(id);
        this.mUrl = url;
        this.setCanDisplay(true);
    }

    private ImageEntry(Parcel in) {
        super(in);
        mUrl = in.readString();
        mCanDisplay = in.readInt() != 0;
    }

    public void setCanDisplay(boolean b) {
        this.mCanDisplay = b;
    }

    public boolean getCanDisplay() {
        return this.mCanDisplay;
    }

    public String getUrl() {
        return mUrl;
    }


    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(mUrl);
        out.writeInt(getCanDisplay() ? 1 : 0);
    }

    public static final Parcelable.Creator<ImageEntry> CREATOR =
            new Parcelable.Creator<ImageEntry>() {
                @Override
                public ImageEntry createFromParcel(Parcel in) {
                    return new ImageEntry(in);
                }

                @Override
                public ImageEntry[] newArray(int size) {
                    return new ImageEntry[size];
                }
            };

}
