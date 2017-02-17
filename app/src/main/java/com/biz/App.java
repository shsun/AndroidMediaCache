package com.biz;

import android.app.Application;
import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;

/**
 * @author Alexey Danilov (danikula@gmail.com).
 */
public class App extends Application {

    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        App app = (App) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
//        return new HttpProxyCacheServer(this);

        // limit total count of files in cache:
//        return new HttpProxyCacheServer.Builder(this)
//                .maxCacheFilesCount(20)
//                .build();

        return new HttpProxyCacheServer.Builder(this)
                .maxCacheSize(1024 * 1024 * 1024)       // 100 M for cache
                .build();
    }
}

