package com.danikula.videocache;

/**
 * Created by shsun on 17/2/16.
 */

public class UrlMime {
    protected int length = Integer.MIN_VALUE;
    protected String mime;

    public UrlMime() {
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }
}