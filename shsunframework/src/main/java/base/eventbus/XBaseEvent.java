package base.eventbus;


import java.util.Date;

/**
 * @author shsun
 */
public class XBaseEvent<T> {

    private T mData;


    public XBaseEvent(T data) {
        this.setData(data);
    }

    public void setData(T mData) {
        this.mData = mData;
    }

    public T getData() {
        return mData;
    }

    public Object getEventSource() {
        return null;
    }


    public Date getEventTime() {
        return new Date();
    }
}
