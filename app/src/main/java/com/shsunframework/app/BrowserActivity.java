package com.shsunframework.app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.biz.R;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


/**
 *
 */
public class BrowserActivity extends BaseAppCompatActivity {

    private static final String TAG = "BrowserActivity";

    public static final String URL = "URL";
    public static final String CACHE_MODE = "CACHE_MODE";

    private String mUrl;
    private int mCacheMode;

    private WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, Bundle prevInstanceState) {
        // , "http://www.163.com"
        mUrl = this.getIntent().getStringExtra(URL);
        mCacheMode = this.getIntent().getIntExtra(CACHE_MODE, WebSettings.LOAD_NO_CACHE);
    }

    @Override
    protected void initView(Bundle savedInstanceState, Bundle prevInstanceState) {
        setContentView(R.layout.activity_browser);

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
