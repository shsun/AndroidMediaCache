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

    private HttpProxyCacheServer mProxyServer;

    private Stack<Activity> mActivityStack;

    private static CZSZApplication theSingletonInstance;

    // Returns the application instance
    public static CZSZApplication getInstance() {
        return theSingletonInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        theSingletonInstance = this;
    }

    public HttpProxyCacheServer getProxyCacheServer() {
        if (mProxyServer == null) {
            // limit total count of files in cache:
//            mProxyServer = new HttpProxyCacheServer.Builder(this)
//                    .maxCacheFilesCount(20)
//                    .build();

            mProxyServer = new HttpProxyCacheServer.Builder(this)
                    .maxCacheSize(1024 * 1024 * 1024)       // 100 M for cache
                    .build();
        }
        return mProxyServer;
    }

    /**
     * add Activity 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = mActivityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = mActivityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
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

