package com.danikula.videocache;

/**
 * Created by shsun on 17/2/16.
 */

public abstract class UrlSource implements Source {
    protected String url;
    protected volatile int length = Integer.MIN_VALUE;
    protected volatile String mime;

    protected IMimeCache mimeCache;

    public UrlSource(String url) {
        this.url = url;
    }

    public UrlSource(UrlSource urlSource) {
        this.url = urlSource.url;
        this.length = urlSource.length;
        this.mime = urlSource.mime;
    }

    public abstract String getMime() throws ProxyCacheException;

    @Override
    public String toString() {
        return "UrlSource{" +
                "url='" + url + '\'' +
                ", length=" + length +
                ", mime='" + mime + '\'' +
                '}';
    }
}
