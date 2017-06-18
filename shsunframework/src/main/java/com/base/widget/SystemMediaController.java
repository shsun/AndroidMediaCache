package com.base.widget;

import android.media.MediaPlayer;
import android.os.SystemClock;

/**
 * default abstract for cooder
 *
 * @author shsun
 */
public class SystemMediaController implements android.widget.MediaController.MediaPlayerControl {

    private static String CLASSTAG = "SystemMediaController";

    private BaseSystemVideoView.VideoAdViewListener mVideoAdRenderer;
    private BaseSystemVideoView mVideoAdView;
    private MediaPlayer mMediaPlayer;
    private boolean pausedByController = false;

    private static long REWIND_INTERVAL = 1000L;
    private long lastRewindTime = 0L;

    /**
     * @param hostRenderer
     * @param view
     * @param player
     */
    public SystemMediaController(BaseSystemVideoView.VideoAdViewListener hostRenderer, BaseSystemVideoView view, MediaPlayer player) {
        this.mVideoAdRenderer = hostRenderer;
        this.mVideoAdView = view;
        this.mMediaPlayer = player;
    }

    @Override
    public void start() {
        this.mVideoAdView.start();
        if (this.pausedByController) {
            this.pausedByController = true;
            this.mVideoAdRenderer.onAdResumed();
        }
    }

    @Override
    public void pause() {
        this.mVideoAdView.pause();
        this.pausedByController = true;
        this.mVideoAdRenderer.onAdPaused();
    }

    @Override
    public int getDuration() {
        return (int) this.mVideoAdView.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return (int) this.mVideoAdView.getPlayheadTime();
    }

    @Override
    public void seekTo(int msec) {
        if (msec >= this.mMediaPlayer.getCurrentPosition()) {
            return;
        }
        this.mVideoAdView.seekTo(msec);

        long currentTime = SystemClock.elapsedRealtime();

        if (currentTime > this.lastRewindTime + REWIND_INTERVAL) {
            this.mVideoAdRenderer.onAdRewind();
        }
        this.lastRewindTime = currentTime;
    }

    @Override
    public boolean isPlaying() {
        return (this.mVideoAdView.isInPlaybackState()) && (this.mMediaPlayer.isPlaying());
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    protected void onImprTimer() {

        // mAdLogger.i(CLASSTAG, "onImprTimer");
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
