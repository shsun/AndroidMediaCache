package base.entry;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 */
public class XParcelableDate implements Parcelable {

    public long mId;
    public Date mDate;

    public XParcelableDate(long id, Date date) {
        this.mId = id;
        this.mDate = date;
    }

    public XParcelableDate(long id, long time) {
        this(id, new Date(time));
    }

    public XParcelableDate(Parcel source) {
        mId = source.readLong();
        mDate = new Date(source.readLong());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeLong(mDate.getTime());
    }

    public static final Parcelable.Creator<XParcelableDate> CREATOR = new Creator<XParcelableDate>() {

        @Override
        public XParcelableDate[] newArray(int size) {
            return new XParcelableDate[size];
        }

        @Override
        public XParcelableDate createFromParcel(Parcel source) {
            return new XParcelableDate(source);
        }
    };
}
