package base.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import base.XBaseApplication;
import base.net.XRequestManager;

import org.greenrobot.eventbus.EventBus;

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
        ((XBaseApplication) this.getApplication()).addActivity(this);

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
        ((XBaseApplication) this.getApplication()).finishActivity(this);

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
}
