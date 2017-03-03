package com.shsunframework.eventbus;


import java.util.Date;

/**
 * @author shsun
 */
public class BaseEvent<T> {

    private T mData;


    public BaseEvent(T data) {
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
