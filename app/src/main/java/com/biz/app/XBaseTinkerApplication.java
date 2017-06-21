package com.biz.app;

import android.app.Activity;
import com.tencent.tinker.loader.app.TinkerApplication;
import java.util.Stack;

/**
 * Created by shsun on 6/21/17.
 */

public class XBaseTinkerApplication extends TinkerApplication {

    private Stack<Activity> mActivityStack;

    public XBaseTinkerApplication(int tinkerFlags) {
        super(tinkerFlags);
    }

    public XBaseTinkerApplication(int tinkerFlags, String delegateClassName, String loaderClassName, boolean tinkerLoadVerifyFlag) {
        super(tinkerFlags, delegateClassName, loaderClassName, tinkerLoadVerifyFlag);
    }

    public XBaseTinkerApplication(int tinkerFlags, String delegateClassName) {
        super(tinkerFlags, delegateClassName);
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
     * getViewHolder current Activity 获取当前Activity（栈中最后一个压入的）
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
        try {
            finishAllActivity();
        } catch (Throwable e) {
            //
        }

        super.onTerminate();

        System.exit(0);
    }

}
