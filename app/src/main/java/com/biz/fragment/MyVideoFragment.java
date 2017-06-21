package com.biz.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.VideoView;

//import com.biz.CZSZApplication;
import com.biz.R;
import com.biz.app.SampleApplicationLike;
import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import base.controller.XBaseFragment;

import java.io.File;

/**
 *     ORANGE_1("http://219.238.4.104/video07/2013/12/17/779163-102-067-2207_1.mp4"),
 ORANGE_2("http://219.238.4.104/video07/2013/12/17/779163-102-067-2207_2.mp4"),
 ORANGE_3("http://219.238.4.104/video07/2013/12/17/779163-102-067-2207_3.mp4"),
 ORANGE_4("http://219.238.4.104/video07/2013/12/17/779163-102-067-2207_4.mp4"),
 ORANGE_5("http://219.238.4.104/video07/2013/12/17/779163-102-067-2207_5.mp4");
 */
public class MyVideoFragment extends XBaseFragment {

    public static final String TAG = MyVideoFragment.class.getSimpleName();

    private String mRemoteVideoURL;
    private String mCachedVideoURL;

    private ImageView mCacheStatusImageView;
    private VideoView mVideoView;
    private SeekBar mSeekBar;

    private final VideoProgressUpdater mUpdater = new VideoProgressUpdater();

    CacheListener mCacheListener = new CacheListener() {
        @Override
        public void onCacheAvailable(File file, String url, int percentsAvailable) {
            mSeekBar.setSecondaryProgress(percentsAvailable);
            setCachedState(percentsAvailable == 100);
            Log.d(TAG, String.format("onCacheAvailable. percents: %d, file: %s, mRemoteVideoURL: %s", percentsAvailable, file, url));
        }
    };

    @SuppressLint("WrongViewCast")
    @Override
    public View initView(Bundle bundle, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.my_fragment_video, null);
        mCacheStatusImageView = (ImageView) view.findViewById(R.id.cacheStatusImageView);
        mSeekBar = (SeekBar) view.findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekVideo();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                ;
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                ;
            }
        });
        mVideoView = (VideoView) view.findViewById(R.id.videoView);
        return view;
    }

    @Override
    public void initData(Bundle bundle) {
        //
        mRemoteVideoURL = "http://219.238.4.104/video07/2013/12/17/779163-102-067-2207_5.mp4";
        mCachedVideoURL = this.getContext().getExternalCacheDir().getAbsolutePath();

        checkCachedState();
        startVideo();
    }


    private void checkCachedState() {

        /*
        HttpProxyCacheServer proxy = CZSZApplication.getInstance().getProxyCacheServer();
        boolean fullyCached = proxy.isCached(mRemoteVideoURL);
        setCachedState(fullyCached);
        if (fullyCached) {
            mSeekBar.setSecondaryProgress(100);
        }
        */
    }

    private void startVideo() {
        /*
        HttpProxyCacheServer proxy = CZSZApplication.getInstance().getProxyCacheServer();
        proxy.registerCacheListener(mCacheListener, mRemoteVideoURL);
        String proxyUrl = proxy.getProxyUrl(mRemoteVideoURL);
        Log.d(TAG, "Use proxy mRemoteVideoURL " + proxyUrl + " instead of original mRemoteVideoURL " + mRemoteVideoURL);
        mVideoView.setVideoPath(proxyUrl);
        mVideoView.start();
        */
    }

    private void setCachedState(boolean cached) {
        final int statusIconId = cached ? R.drawable.ic_cloud_done : R.drawable.ic_cloud_download;
        mCacheStatusImageView.setImageResource(statusIconId);
    }

    private void seekVideo() {
        int videoPosition = mVideoView.getDuration() * mSeekBar.getProgress() / 100;
        mVideoView.seekTo(videoPosition);
    }


    protected void onVisible() {
        super.onVisible();
    }

    protected void onInvisible() {
        Log.d(TAG, "onInvisible");
        if (mVideoView != null && mUpdater != null) {
            mVideoView.pause();
            mUpdater.stop();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mUpdater.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mVideoView.pause();
        mUpdater.stop();
    }


    @Override
    public void onStop() {
        super.onStop();
        mVideoView.pause();
        mUpdater.stop();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mVideoView.pause();
        mUpdater.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        /*
        mVideoView.stopPlayback();
        CZSZApplication.getInstance().getProxyCacheServer().unregisterCacheListener(mCacheListener);
        */
    }

    private void updateVideoProgress() {
        int videoProgress = mVideoView.getCurrentPosition() * 100 / mVideoView.getDuration();
        mSeekBar.setProgress(videoProgress);
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
