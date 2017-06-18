package base.controller;

import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import base.R;
//import com.tencent.smtt.export.external.interfaces.SslError;
//import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
//import com.tencent.smtt.sdk.WebSettings;
//import com.tencent.smtt.sdk.WebView;
//import com.tencent.smtt.sdk.WebViewClient;

import com.github.barteksc.pdfviewer.PDFView;

/**
 * Created by shsun on 6/17/17.
 */
public class XBasePDFActivity extends XBaseAppCompatActivity {

    private static final String TAG = "XBasePDFActivity";

    public static final String PDF_READER_KEY_TITLE = "PDF_READER_KEY_TITLE";
    public static final String PDF_READER_KEY_URL = "PDF_READER_KEY_URL";

    private String mTitle;
    private String mUrl;

    private PDFView mPDFView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, Bundle prevInstanceState) {
        // , "http://www.163.com"
        mTitle = this.getIntent().getStringExtra(PDF_READER_KEY_TITLE);
        mUrl = this.getIntent().getStringExtra(PDF_READER_KEY_URL);

        mUrl = "http://pg.jrj.com.cn/acc/Res/CN_RES/MAC/2012/5/21/01bf002b-035e-4697-bf44-4225949f12c2.pdf";
    }

    @Override
    protected void initView(Bundle savedInstanceState, Bundle prevInstanceState) {
        setContentView(R.layout.activity_android_pdf_viewer);

        // Toast.makeText(this, TAG, Toast.LENGTH_SHORT).show();
        mPDFView = (PDFView) this.findViewById(R.id.pdfView);
        mPDFView.fromUri(Uri.parse(mUrl));
    }

    @Override
    protected void initData(Bundle savedInstanceState, Bundle prevInstanceState) {

    }
}
