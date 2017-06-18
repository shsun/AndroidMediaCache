package com.base.net;

/**
 * @author shsun
 */
public interface XHttpRequestCallback {
    /**
     *
     * @param content
     */
    public void onSuccess(String content);

    /**
     *
     * @param errorMessage
     */
    public void onFail(String errorMessage);

    /**
     * 
     */
    public void onCookieExpired();
}
