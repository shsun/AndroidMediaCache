package base.controller;

import base.XBaseApplication;
import base.XBaseTinkerApplication;
import base.eventbus.XBaseEvent;
import base.net.XRequestManager;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by shsun on 17/2/18.
 */

public abstract class XBaseActivity extends Activity implements IXController {

    /**
     *
     */
    protected XRequestManager requestManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestManager = new XRequestManager();
        EventBus.getDefault().register(this);


        Application abc = this.getApplication();

        ((XBaseTinkerApplication) this.getApplication()).addActivity(this);



        Bundle bundle = this.getIntent().getExtras();
        //
        initVariables(savedInstanceState, bundle);
        initView(savedInstanceState, bundle);
        initData(savedInstanceState, bundle);
    }

    protected abstract void initVariables(Bundle savedInstanceState, Bundle prevInstanceState);

    protected abstract void initView(Bundle savedInstanceState, Bundle prevInstanceState);

    protected abstract void initData(Bundle savedInstanceState, Bundle prevInstanceState);


    @Override
    protected void onDestroy() {
        this.cancelAllRequest();

        EventBus.getDefault().unregister(this);
        ((XBaseApplication) this.getApplication()).finishActivity(this);

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        this.cancelAllRequest();
        super.onPause();
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
