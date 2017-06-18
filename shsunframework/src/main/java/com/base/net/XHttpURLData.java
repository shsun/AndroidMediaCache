package com.base.net;

/**
 * @author shsun
 */
public class XHttpURLData {

    private String key;
    private long expires;
    private String requestMethod = XHttpRequest.RequestMethod.GET.getMethod();
    private String url;
    private String mockClass;

    public XHttpURLData() {
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
