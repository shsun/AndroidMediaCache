package com.shsunframework.entry;

/**
 * Created by shsun on 17/2/19.
 */

public class BaseEntry {

    private final String mId;

    public BaseEntry(String id) {
        this.mId = id;
    }

    public String getId() {
        return mId;
    }
}
