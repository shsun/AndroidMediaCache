package base.os;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shsun on 17/1/18.
 */
public abstract class XAbstractExtraInfo implements Parcelable {

    public int mIntTesting4;
    public String mStringTesting4;

    public XAbstractExtraInfo(String prodType) {
        this.mIntTesting4 = 999;
        this.mStringTesting4 = "this is the test string";
    }

    protected XAbstractExtraInfo(Parcel in) {
        // 注意顺序和writeToParcel 保持一致，因为是按顺序读写的
        mIntTesting4 = in.readInt();
        mStringTesting4 = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        // 注意顺序，需要和构造函数内保持一致，因为是按顺序读写的
        out.writeInt(mIntTesting4);
        out.writeString(this.mStringTesting4);
    }
}
