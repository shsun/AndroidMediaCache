package com.base.net;

import android.content.res.XmlResourceParser;

/**
 * @author shsun
 */
public class XHttpURLData {

    private String key;
    private long expires;
    private String requestMethod = XHttpRequest.RequestMethod.GET.getMethod();
    private String url;
    private String mockClass;

    public XHttpURLData(XmlResourceParser parser) {
        this.setKey(parser.getAttributeValue(null, "Key"));
        this.setExpires(Long.parseLong(parser.getAttributeValue(null, "Expires")));
        this.setRequestMethod(parser.getAttributeValue(null, "NetType"));
        this.setMockClass(parser.getAttributeValue(null, "MockClass"));
        this.setUrl(parser.getAttributeValue(null, "Url"));
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String method) {
        this.requestMethod = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMockClass() {
        return mockClass;
    }

    public void setMockClass(String mockClass) {
        this.mockClass = mockClass;
    }
}
