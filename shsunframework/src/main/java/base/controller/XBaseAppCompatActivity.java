package base.controller;

import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

//import com.biz.entry.PersonEntry;
import base.XBaseTinkerApplication;
import base.eventbus.XBaseEvent;
import base.net.XRequestManager;
import com.google.gson.Gson;
import base.XBaseSystemApplication;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by shsun on 17/2/18.
 */

public abstract class XBaseAppCompatActivity extends AppCompatActivity implements IXController {
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

        this.initVariables(savedInstanceState, bundle);
        this.initView(savedInstanceState, bundle);

        OkHttpUtils
                .get()//
                .url("https://github.com/hongyangAndroid")//
                .addParams("username", "hyman")//
                .addParams("password", "123")//
                .build()//
                .execute(new com.zhy.http.okhttp.callback.Callback() {
                    @Override
                    public Object parseNetworkResponse(okhttp3.Response response) throws Exception {
                        String string = response.body().string();
                        Object user = new Gson().fromJson(string, Object.class);
                        return user;
                    }

                    @Override
                    public void onError(okhttp3.Call call, Exception e) {
                        // run on UIThread
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, Object o) {
                        // run on UIThread
                    }
                });


        this.initData(savedInstanceState, bundle);
    }

    protected abstract void initVariables(Bundle savedInstanceState, Bundle prevInstanceState);

    protected abstract void initView(Bundle savedInstanceState, Bundle prevInstanceState);

    protected abstract void initData(Bundle savedInstanceState, Bundle prevInstanceState);

    @Override
    protected void onPause() {
        this.cancelAllRequest();

        super.onPause();
    }

    /**
     *
     */
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
