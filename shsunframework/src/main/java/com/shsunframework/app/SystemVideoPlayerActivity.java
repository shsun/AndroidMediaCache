package com.shsunframework.app;

import java.util.Map;

import com.shsunframework.R;
import com.shsunframework.widget.BaseSystemVideoView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 *
 */
public class SystemVideoPlayerActivity extends BaseActivity {

    public static final String TAG = "SystemVideoPlayerActivity";

    public static final String VIDEO_PLAYER_KEY_URL = "VIDEO_PLAYER_KEY_URL";

    String vieoPath = "http://219.238.4.104/video07/2013/12/17/779163-102-067-2207_5.mp4";


    protected BaseSystemVideoView videoAdView;


    @Override
    protected void initVariables(Bundle savedInstanceState, Bundle prevInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState, Bundle prevInstanceState) {
        final int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window = this.getWindow();
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_system_video_player);
        videoAdView = (BaseSystemVideoView) findViewById(R.id.system_video_view);
        videoAdView.setVideoAdViewListener(new BaseSystemVideoView.VideoAdViewListener() {
            @Override
            public void onAdViewStart() {

            }

            @Override
            public void onAdVideoViewError(Map<String, Object> map) {

            }

            @Override
            public void onAdViewLoaded() {

            }

            @Override
            public void onAdViewMediaPrepared() {

            }

            @Override
            public void onAdViewClicked() {

            }

            @Override
            public void onAdUnMuted() {

            }

            @Override
            public void onAdMuted() {

            }

            @Override
            public void onAdVideoViewComplete() {

            }

            @Override
            public void onAdViewSurfaceDestroyed() {

            }

            @Override
            public void onAdResumed() {

            }

            @Override
            public void onAdPaused() {

            }

            @Override
            public void onAdRewind() {

            }

            @Override
            public void setWidth(int width) {

            }

            @Override
            public int getWidth() {
                return 0;
            }

            @Override
            public void setHeight(int height) {

            }

            @Override
            public int getHeight() {
                return 0;
            }
        });

    }

    @Override
    protected void initData(Bundle savedInstanceState, Bundle prevInstanceState) {
        videoAdView.setAdUrl(vieoPath);
        videoAdView.start();
    }
}
