package com.biz;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;

import java.util.Stack;

/**
 * @author Alexey Danilov (danikula@gmail.com).
 */
public class CZSZApplication extends Application {

    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        CZSZApplication app = (CZSZApplication) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        // limit total count of files in cache:
//        return new HttpProxyCacheServer.Builder(this)
//                .maxCacheFilesCount(20)
//                .build();

        return new HttpProxyCacheServer.Builder(this)
                .maxCacheSize(1024 * 1024 * 1024)       // 100 M for cache
                .build();
    }


    private static Stack<Activity> activityStack;
    private static CZSZApplication singleton;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }

    // Returns the application instance
    public static CZSZApplication getInstance() {
        return singleton;
    }

    /**
     * add Activity 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    @Override
    public void onTerminate() {
        super.onTerminate();

        try {
            finishAllActivity();
        } catch (Throwable e) {
            //
        }

        System.exit(0);
    }


}

