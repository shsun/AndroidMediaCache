package com.shsunframework.app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.shsunframework.R;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import com.shsunframework.BaseApplication;
/**
 *
 */
public class QQX5BrowserActivity extends BaseAppCompatActivity {

    private static final String TAG = "QQX5BrowserActivity";


    public static final String QQ_X5_BROWSER_KEY_TITLE = "QQ_X5_BROWSER_KEY_TITLE";
    public static final String QQ_X5_BROWSER_KEY_URL = "QQ_X5_BROWSER_KEY_URL";
    public static final String QQ_X5_BROWSER_KEY_CACHE_MODE = "QQ_X5_BROWSER_KEY_CACHE_MODE";

    private String mTitle;
    private String mUrl;
    private int mCacheMode;

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, Bundle prevInstanceState) {
        // , "http://www.163.com"
        mTitle = this.getIntent().getStringExtra(QQ_X5_BROWSER_KEY_TITLE);
        mUrl = this.getIntent().getStringExtra(QQ_X5_BROWSER_KEY_URL);
        mCacheMode = this.getIntent().getIntExtra(QQ_X5_BROWSER_KEY_CACHE_MODE, WebSettings.LOAD_NO_CACHE);
    }

    @Override
    protected void initView(Bundle savedInstanceState, Bundle prevInstanceState) {
        setContentView(R.layout.activity_qq_x5_browser);

        // Toast.makeText(this, TAG, Toast.LENGTH_SHORT).show();
        mWebView = (WebView) this.findViewById(R.id.webView);
        mWebView.getSettings().setCacheMode(mCacheMode);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, String url) {
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "onPageFinished");
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.i(TAG, "onReceivedError");
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                Log.i(TAG, "onLoadResource");
            }

            @Override
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                Log.i(TAG, "onReceivedSslError");
            }
        });
        mWebView.loadUrl(mUrl);
    }

    @Override
    protected void initData(Bundle savedInstanceState, Bundle prevInstanceState) {

    }
}
