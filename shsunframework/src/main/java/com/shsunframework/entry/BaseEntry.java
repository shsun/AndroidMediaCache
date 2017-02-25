package com.shsunframework.entry;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shsun on 17/2/19.
 */


public abstract class BaseEntry implements Parcelable {

    public String mId;


    public int mIntTesting4LM;
    public String mStringTesting4LM;

    public BaseEntry(String id) {
        this.mIntTesting4LM = 999;
        this.mStringTesting4LM = "this is the test string";
        this.mId = id;
    }

    protected BaseEntry(Parcel in) {
        // 注意顺序和writeToParcel 保持一致，因为是按顺序读写的
        mId = in.readString();
        mIntTesting4LM = in.readInt();
        mStringTesting4LM = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        // 注意顺序，需要和构造函数内保持一致，因为是按顺序读写的
        out.writeString(mId);
        out.writeInt(mIntTesting4LM);
        out.writeString(this.mStringTesting4LM);
    }

    public String getId() {
        return mId;
    }

}
