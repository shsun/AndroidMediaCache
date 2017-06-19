package com.biz.entry;

import android.os.Parcel;
import android.os.Parcelable;

import base.entry.XBaseEntry;

/**
 * Created by shsun on 6/19/17.
 */

public class AuthorEntry extends XBaseEntry {

    private String id;
    private String name;
    private String brief;

    public AuthorEntry(String id, String name, String brief) {
        super(id);
        this.name = name;
        this.brief = brief;
    }

    private AuthorEntry(Parcel in) {
        super(in);
        name = in.readString();
        brief = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(name);
        out.writeString(brief);
    }

    public static final Parcelable.Creator<AuthorEntry> CREATOR = new Parcelable.Creator<AuthorEntry>() {
        @Override
        public AuthorEntry createFromParcel(Parcel in) {
            return new AuthorEntry(in);
        }

        @Override
        public AuthorEntry[] newArray(int size) {
            return new AuthorEntry[size];
        }
    };
}
