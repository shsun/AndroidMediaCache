package com.biz.homepage.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.biz.CZSZApplication;
import com.biz.R;
import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.shsunframework.app.BaseFragment;

import java.io.File;


public class MyVideoFragment extends BaseFragment implements CacheListener {

    private static final String LOG_TAG = "VideoFragment";

    //@FragmentArg
    String url;
    //@FragmentArg
    String cachePath;

    //@ViewById
    ImageView cacheStatusImageView;
    //@ViewById
    VideoView videoView;
    //@ViewById
    ProgressBar progressBar;

    private final VideoProgressUpdater updater = new VideoProgressUpdater();


    @SuppressLint("WrongViewCast")
    @Override
    public View initView(Bundle bundle, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.my_fragment_video, null);
        cacheStatusImageView = (ImageView)view.findViewById(R.id.cacheStatusImageView);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        videoView =  (VideoView)view.findViewById(R.id.videoView);
        return view;
    }


    @Override
    public void initData(Bundle bundle) {
        //
        url = "http://219.238.4.104/video07/2013/12/17/779163-102-067-2207_5.mp4";
        cachePath = this.getContext().getExternalCacheDir().getAbsolutePath();

        checkCachedState();
        startVideo();

    }
    @Override
    public void onCacheAvailable(File file, String url, int percentsAvailable) {
        progressBar.setSecondaryProgress(percentsAvailable);
        setCachedState(percentsAvailable == 100);
        Log.d(LOG_TAG, String.format("onCacheAvailable. percents: %d, file: %s, url: %s", percentsAvailable, file, url));
    }

    private void checkCachedState() {
        HttpProxyCacheServer proxy = CZSZApplication.getInstance().getProxyCacheServer();
        boolean fullyCached = proxy.isCached(url);
        setCachedState(fullyCached);
        if (fullyCached) {
            progressBar.setSecondaryProgress(100);
        }
    }

    private void startVideo() {
        HttpProxyCacheServer proxy = CZSZApplication.getInstance().getProxyCacheServer();
        proxy.registerCacheListener(this, url);
        String proxyUrl = proxy.getProxyUrl(url);
        Log.d(LOG_TAG, "Use proxy url " + proxyUrl + " instead of original url " + url);
        videoView.setVideoPath(proxyUrl);
        videoView.start();
    }

    private void setCachedState(boolean cached) {
        int statusIconId = cached ? R.drawable.ic_cloud_done : R.drawable.ic_cloud_download;
        cacheStatusImageView.setImageResource(statusIconId);
    }


    /*
    public static Fragment build(Context context, Video video) {
        return build(video.url, video.getCacheFile(context).getAbsolutePath());
    }

    public static Fragment build(String url, String cachePath) {
        return build(url, cachePath);
    }

    // @AfterViews
    void afterViewInjected() {
        checkCachedState();
        startVideo();
    }


    @Override
    public void onResume() {
        super.onResume();
        updater.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        updater.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        videoView.stopPlayback();
        CZSZApplication.getInstance().getProxyCacheServer().unregisterCacheListener(this);
    }


    // @SeekBarTouchStop(R.id.progressBar)
    void seekVideo() {
        int videoPosition = videoView.getDuration() * progressBar.getProgress() / 100;
        videoView.seekTo(videoPosition);
    }

    */


    private void updateVideoProgress() {
        int videoProgress = videoView.getCurrentPosition() * 100 / videoView.getDuration();
        progressBar.setProgress(videoProgress);
    }


    private final class VideoProgressUpdater extends Handler {

        public void start() {
            sendEmptyMessage(0);
        }

        public void stop() {
            removeMessages(0);
        }

        @Override
        public void handleMessage(Message msg) {
            updateVideoProgress();
            sendEmptyMessageDelayed(0, 500);
        }
    }
}
