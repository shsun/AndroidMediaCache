package com.danikula.videocache;

/**
 * Created by shsun on 17/2/16.
 */

public interface IMimeCache {
    void putMime(String url, int length, String mime);

    UrlMime getMime(String url);
}
