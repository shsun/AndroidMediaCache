package com.biz;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;

import java.util.ArrayList;
import java.util.List;

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


    private List<Activity> activities = new ArrayList<Activity>();

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        for (Activity activity : activities) {
            activity.finish();
        }

        // 释放其他资源的方法，不是系统的。
        // onDestroy();

        System.exit(0);
    }

}

