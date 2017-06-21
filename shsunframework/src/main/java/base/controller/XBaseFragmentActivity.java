package base.controller;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import base.XBaseSystemApplication;
import base.XBaseTinkerApplication;
import base.eventbus.XBaseEvent;
import base.net.XRequestManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by shsun on 17/2/18.
 */

public abstract class XBaseFragmentActivity extends FragmentActivity implements IXController {
    /**
     *
     */
    protected XRequestManager requestManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestManager = new XRequestManager();

        EventBus.getDefault().register(this);

        Application application = this.getApplication();
        if (application instanceof XBaseSystemApplication) {
            ((XBaseSystemApplication) application).addActivity(this);

        } else if (application instanceof XBaseTinkerApplication) {
            ((XBaseTinkerApplication) application).addActivity(this);
        }
        
        Bundle bundle = this.getIntent().getExtras();
        initVariables(savedInstanceState, bundle);
        initView(savedInstanceState, bundle);
        initData(savedInstanceState, bundle);
    }

    protected abstract void initVariables(Bundle savedInstanceState, Bundle prevInstanceState);

    protected abstract void initView(Bundle savedInstanceState, Bundle prevInstanceState);

    protected abstract void initData(Bundle savedInstanceState, Bundle prevInstanceState);

    @Override
    protected void onPause() {
        this.cancelAllRequest();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        this.cancelAllRequest();
        EventBus.getDefault().unregister(this);
        ((XBaseSystemApplication) this.getApplication()).finishActivity(this);

        super.onDestroy();
    }

    @Override
    public XRequestManager getRequestManager() {
        return requestManager;
    }

    private void cancelAllRequest() {
        if (requestManager != null) {
            requestManager.cancelRequest();
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(XBaseEvent event) {
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventAsync(XBaseEvent event) {
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEventBackgroundThread(XBaseEvent event) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(XBaseEvent event) {
    }
}
