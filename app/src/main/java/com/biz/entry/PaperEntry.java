package com.biz.entry;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import base.entry.XBaseEntry;
import base.entry.XParcelableDate;

/**
 * Created by shsun on 6/19/17.
 */

public class PaperEntry extends XBaseEntry {

    public String id;
    public AuthorEntry author;

    public String title;
    public String content;
    public Date date;
    //
    public String source;
    public String[] tags;

    public PaperEntry(String id, AuthorEntry author, String title, String content, Date date, String source) {
        super(id);
        this.author = author;
        this.title = title;
        this.content = content;
        this.date = date;
        this.source = source;

    }

    private PaperEntry(Parcel in) {
        super(in);
        id = in.readString();
        author = in.readParcelable(AuthorEntry.class.getClassLoader());
        this.title = in.readString();
        this.content = in.readString();
        this.date = in.readParcelable(XParcelableDate.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(id);
        out.writeParcelable(author, flags);
        out.writeString(id);
        out.writeString(id);
        out.writeString(id);

    }

    public static final Parcelable.Creator<PaperEntry> CREATOR = new Parcelable.Creator<PaperEntry>() {
        @Override
        public PaperEntry createFromParcel(Parcel in) {
            return new PaperEntry(in);
        }

        @Override
        public PaperEntry[] newArray(int size) {
            return new PaperEntry[size];
        }
    };

}
