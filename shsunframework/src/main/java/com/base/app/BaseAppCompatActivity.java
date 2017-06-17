package com.base.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

//import com.biz.entry.PersonEntry;
import com.google.gson.Gson;
import com.base.BaseApplication;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by shsun on 17/2/18.
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseApplication) this.getApplication()).addActivity(this);

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

    /**
     *
     * @param savedInstanceState
     * @param prevInstanceState
     */
    protected abstract void initVariables(Bundle savedInstanceState, Bundle prevInstanceState);

    /**
     *
     * @param savedInstanceState
     * @param prevInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState, Bundle prevInstanceState);

    /**
     *
     * @param savedInstanceState
     * @param prevInstanceState
     */
    protected abstract void initData(Bundle savedInstanceState, Bundle prevInstanceState);


    /**
     *
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((BaseApplication) this.getApplication()).finishActivity(this);
    }
}
