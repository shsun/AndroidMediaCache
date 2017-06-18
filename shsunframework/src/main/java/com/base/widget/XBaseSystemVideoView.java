package com.base.widget;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

/**
 * default default abstract for cooder
 * 
 * @author shsun
 * 
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class XBaseSystemVideoView extends SurfaceView implements SurfaceHolder.Callback, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnVideoSizeChangedListener, View.OnClickListener {

    public interface VideoAdViewListener {
        void onAdViewStart();
        void onAdVideoViewError(Map<String, Object> map);
        void onAdViewLoaded();
        void onAdViewMediaPrepared();
        void onAdViewClicked();
        void onAdUnMuted();
        void onAdMuted();
        void onAdVideoViewComplete();
        void onAdViewSurfaceDestroyed();
        //void onImprTimer();

        void onAdResumed();
        void onAdPaused();
        void onAdRewind();

        //
        void setWidth(int width);

        int getWidth();

        void setHeight(int height);

        int getHeight();
    }



    private static final String TAG = "VideoAdView";

    public static enum VideoState {
        /* err */
        ERROR("error"),

        /* idle */
        IDLE("idle"),

        /* preloading & preloaded */
        PREPARING("preparing"), PREPARED("prepared"),

        /* playing & paused */
        PLAYING("playing"), PAUSED("paused"),

        /* completed */
        PLAYBACK_COMPLETED("playback_completed");

        /**
         * default
         * 
         * @param value
         */
        private VideoState(String value) {
            this.value = value;
        }

        private final String value;

        public String getValue() {
            return this.value;
        }

        public static VideoState parse(String val) {
            for (VideoState state : VideoState.values()) {
                if (state.value.equalsIgnoreCase(val)) {
                    return state;
                }
            }
            return null;
        }
    }

    private VideoState mCurrentState = VideoState.IDLE;
    private VideoState mTargetState = VideoState.IDLE;
    /**
     * default
     * 
     */
    private MediaPlayer mediaPlayer;
    private MediaController mediaController;
    //private XSystemMediaController videoAdController;
    /**
     * default
     * 
     */
    private VideoAdViewListener hostRenderer;
    private String adUrl;
    private SurfaceHolder surfaceHolder = null;
    private int mSurfaceWidth;
    private int mSurfaceHeight;
    /**
     * default
     * 
     */
    private int mDuration;
    /**
     * default
     * 
     */
    private int mSeekWhenPrepared;
    /**
     * default
     * 
     */
    private int mAdWidth;
    private int mAdHeight;
    /**
     * default
     * 
     */
    private float mVolume;
    /**
     * default
     * 
     */
    private AudioManager audioManager;
    /**
     * default
     * 
     */
    private MuteState isMuted;
    private float volumeScale;
    /**
     * default Android 3.2 Honeycomb
     */
    private final boolean preloadEnabled = Build.VERSION.SDK_INT > 13;
    private boolean preloading = false;
    private MediaPlayer.OnErrorListener errorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            XBaseSystemVideoView.this.mCurrentState = VideoState.ERROR;
            XBaseSystemVideoView.this.mTargetState = VideoState.ERROR;
            if (XBaseSystemVideoView.this.mediaController != null) {
                XBaseSystemVideoView.this.mediaController.hide();
            }

            //
            Map<String, Object> errMap = new HashMap<String, Object>();
            String msg;
            // 200
            if (what == MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK) {
                errMap.put("INFO_KEY_ERROR_CODE", "ERROR_UNKNOWN");
                msg = "The video is streamed and its container is not valid for progressive playback i.e the video's "
                        + "index (e.g moov atom) is not at the start of the file.";
            } else {
                // 1
                if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
                    errMap.put("INFO_KEY_ERROR_CODE", "ERROR_UNKNOWN");
                    // msg = "media file format is not recognized.";
                    msg = "loading media file fail";
                    // MEDIA_ERROR_MALFORMED = -1007
                    // MEDIA_ERROR_IO = -1004
                    // MEDIA_ERROR_UNSUPPORTED = -1010
                    // MEDIA_ERROR_TIMED_OUT = -110
                } else {
                    // 100
                    if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
                        msg = "media server has gone away.";
                    } else {
                        msg = "unknown common IO error.";
                    }
                    errMap.put("INFO_KEY_ERROR_CODE", "ERROR_IO");
                }
            }
            errMap.put("INFO_KEY_ERROR_INFO", msg);

            XBaseSystemVideoView.this.hostRenderer.onAdVideoViewError(errMap);
            return true;
        }
    };
    MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            XBaseSystemVideoView.this.mCurrentState = VideoState.PREPARED;
            if (XBaseSystemVideoView.this.preloading) {
                XBaseSystemVideoView.this.hostRenderer.onAdViewLoaded();
                return;
            }

            int seekToPosition = XBaseSystemVideoView.this.mSeekWhenPrepared;
            if (seekToPosition != 0) {
                XBaseSystemVideoView.this.seekTo(seekToPosition);
            }
            if (XBaseSystemVideoView.this.mediaController != null) {
                XBaseSystemVideoView.this.mediaController.setEnabled(true);
            }
            if ((XBaseSystemVideoView.this.mAdWidth != 0) && (XBaseSystemVideoView.this.mAdHeight != 0)) {
                int width = XBaseSystemVideoView.this.mAdWidth;
                int height = XBaseSystemVideoView.this.mAdHeight;
                XBaseSystemVideoView.this.getHolder().setFixedSize(width, height);
                if ((XBaseSystemVideoView.this.mSurfaceWidth == XBaseSystemVideoView.this.mAdWidth)
                        && (XBaseSystemVideoView.this.mSurfaceHeight == XBaseSystemVideoView.this.mAdHeight)) {
                    if (XBaseSystemVideoView.this.mTargetState == VideoState.PLAYING) {
                        XBaseSystemVideoView.this.start();
                        if (XBaseSystemVideoView.this.mediaController != null) {
                            XBaseSystemVideoView.this.mediaController.show();
                        }
                    } else if ((!XBaseSystemVideoView.this.isInPlaybackState()) && (!XBaseSystemVideoView.this.mediaPlayer.isPlaying())
                            && ((seekToPosition != 0) || (XBaseSystemVideoView.this.getPlayheadTime() > 0.0D))) {
                        if (XBaseSystemVideoView.this.mediaController != null) {
                            XBaseSystemVideoView.this.mediaController.show(0);
                        }
                    }
                }
            } else if (XBaseSystemVideoView.this.mTargetState == VideoState.PLAYING) {
                XBaseSystemVideoView.this.start();
            }

            XBaseSystemVideoView.this.hostRenderer.onAdViewMediaPrepared();
        }
    };
    
    /**
     * default
     * 
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.DONUT)
    private XBaseSystemVideoView(Context context) {
        super(context);
        init();
    }
    public XBaseSystemVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XBaseSystemVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public XBaseSystemVideoView(Context context, ViewGroup.LayoutParams lp,
                                boolean withMediaController) {
        this(context);
        init();

        this.mediaController = (withMediaController ? new MediaController(context) : null);
        this.mAdWidth = this.hostRenderer.getWidth();
        this.mAdHeight = this.hostRenderer.getHeight();
        setLayoutParams(lp);
        if (!this.preloadEnabled) {
            requestLayout();
        }
    }


    private void init(){
        //this.mVolume = 0.0F;
        initVideoView();
        setOnClickListener(this);
        this.audioManager = ((AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE));
        this.isMuted = MuteState.UNINITIALIZED;
    }






    public void setVideoAdViewListener(VideoAdViewListener listener){
        this.hostRenderer = listener;
    }



    @SuppressWarnings("deprecation")
    private void initVideoView() {
        getHolder().addCallback(this);
        getHolder().setType(3);
        // setFocusable(true);
        // setFocusableInTouchMode(true);
        // requestFocus();
        this.mCurrentState = VideoState.IDLE;
        this.mTargetState = VideoState.IDLE;
    }

    public VideoState getCurrentState() {
        return mCurrentState;
    }

    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        this.mSurfaceWidth = w;
        this.mSurfaceHeight = h;
        boolean isValidState = this.mTargetState == VideoState.PLAYING;
        boolean hasValidSize = (this.mAdWidth == w) && (this.mAdHeight == h);
        if ((this.mediaPlayer != null) && (isValidState) && (hasValidSize)) {
            if (this.mSeekWhenPrepared != 0) {
                seekTo(this.mSeekWhenPrepared);
            }
            start();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.surfaceHolder = holder;
        if (this.preloading) {
            this.preloading = false;
            startPreloadedVideo();
        } else {
            openVideo();
        }
    }

    private void prepareMediaController() {
        if (this.mediaController != null) {
            // TODO
            //this.videoAdController = new XSystemMediaController(this.hostRenderer, this, this.mediaPlayer);
            //this.mediaController.setMediaPlayer(this.videoAdController);
            this.mediaController.setAnchorView(this);
            this.mediaController.setEnabled(isInPlaybackState());
        }
    }

    private void startPreloadedVideo() {
        this.mDuration = -1;
        this.doCommonConfig4MediaPlayer();
        if (this.mCurrentState == VideoState.PREPARED) {
            this.preparedListener.onPrepared(this.mediaPlayer);
            prepareMediaController();
        } else {
            this.mCurrentState = VideoState.ERROR;
            this.mTargetState = VideoState.ERROR;
            //
            Map<String, Object> errMap = new HashMap<String, Object>();
            errMap.put("INFO_KEY_ERROR_CODE", "ERROR_UNKNOWN");
            errMap.put("INFO_KEY_ERROR_INFO", "MediaPlayer should in prepared state when start play");
            // this.hostRenderer.onAdVideoViewError(errMap);
        }
    }

    private void openVideo() {
        if (this.surfaceHolder == null) {
            return;
        }
        release(false);
        Map<String, Object> errMap = new HashMap<String, Object>();
        try {
            this.mDuration = -1;
            this.mediaPlayer = new MediaPlayer();
            this.doCommonConfig4MediaPlayer();
            this.doCreativeLoading4MediaPlayer();
            this.mCurrentState = VideoState.PREPARING;
            prepareMediaController();
        } catch (IllegalArgumentException e) {
            this.mCurrentState = VideoState.ERROR;
            this.mTargetState = VideoState.ERROR;
            //
            errMap.put("INFO_KEY_ERROR_CODE", "ERROR_INVALID_VALUE");
            errMap.put("INFO_KEY_ERROR_INFO", e.getMessage());
            this.hostRenderer.onAdVideoViewError(errMap);
        } catch (IOException e) {
            this.mCurrentState = VideoState.ERROR;
            this.mTargetState = VideoState.ERROR;

            errMap.put("INFO_KEY_ERROR_CODE", "ERROR_IO");
            errMap.put("INFO_KEY_ERROR_INFO", "Unable to open content: " + this.adUrl + ", error: " + e.toString());
            this.hostRenderer.onAdVideoViewError(errMap);
        }
    }

    private void doCommonConfig4MediaPlayer() {
        this.mediaPlayer.setDisplay(this.surfaceHolder);
        this.mediaPlayer.setOnErrorListener(this.errorListener);
        this.mediaPlayer.setOnCompletionListener(this);
        this.mediaPlayer.setOnVideoSizeChangedListener(this);
        this.mediaPlayer.setScreenOnWhilePlaying(true);
    }

    private void doCreativeLoading4MediaPlayer() throws IllegalArgumentException, SecurityException,
            IllegalStateException, IOException {
        this.mediaPlayer.setDataSource(this.adUrl);
        this.mediaPlayer.setOnPreparedListener(this.preparedListener);
        this.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        this.mediaPlayer.prepareAsync();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.surfaceHolder = null;
        if (this.mediaController != null) {
            this.mediaController.hide();
        }
        if (this.hostRenderer != null) {
            this.hostRenderer.onAdViewSurfaceDestroyed();
        }
        dispose();
    }

    public void startPlayback() {
        start();
        this.hostRenderer.onAdViewStart();
    }

    public void stop() {
        if (isInPlaybackState()) {
            this.mediaPlayer.stop();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
            this.mCurrentState = VideoState.IDLE;
            this.mTargetState = VideoState.IDLE;
        }
    }

    public void dispose() {
        release(true);
    }

    public void pause() {
        Boolean t = isInPlaybackState();
        if (t && this.mediaPlayer.isPlaying()) {
            this.mSeekWhenPrepared = this.mediaPlayer.getCurrentPosition();
            this.mediaPlayer.pause();
            this.mCurrentState = VideoState.PAUSED;
        }

        this.mTargetState = VideoState.PAUSED;
    }

    public void start() {
        if (isInPlaybackState()) {
            this.mediaPlayer.start();
            this.mCurrentState = VideoState.PLAYING;
        }
        this.mTargetState = VideoState.PLAYING;
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        if (height != 0 && width != 0) {
            this.mAdHeight = height;
            this.mAdWidth = width;
            this.hostRenderer.setHeight(this.mAdHeight);
            this.hostRenderer.setWidth(this.mAdWidth);
        }
        getHolder().setFixedSize(this.mAdWidth, this.mAdHeight);
    }

    private void toggleMediaControlsVisibility() {
        if (this.mediaController == null) {
            return;
        }

        if (this.mediaController.isShowing()) {
            this.mediaController.hide();
        } else {
            this.mediaController.show();
        }
    }

    public void onClick(View v) {
        if (this.mediaController != null) {
            toggleMediaControlsVisibility();
        } else if (isInPlaybackState()) {
            this.hostRenderer.onAdViewClicked();
        } else {
            //"ignore click if not in playback state, current state " + this.mCurrentState);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        this.mCurrentState = VideoState.PLAYBACK_COMPLETED;
        this.mTargetState = VideoState.PLAYBACK_COMPLETED;
        if (this.mediaController != null) {
            this.mediaController.hide();
        }
        this.hostRenderer.onAdVideoViewComplete();
    }

    public void setAdUrl(String url) {
        this.adUrl = url;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = getDefaultSize(this.mAdWidth, widthMeasureSpec);
        int measuredHeight = getDefaultSize(this.mAdHeight, heightMeasureSpec);

        if ((this.mAdWidth > 0) && (this.mAdHeight > 0)) {
            if (this.mAdWidth * measuredHeight > measuredWidth * this.mAdHeight) {
                measuredHeight = measuredWidth * this.mAdHeight / this.mAdWidth;
            } else if (this.mAdWidth * measuredHeight < measuredWidth * this.mAdHeight) {
                measuredWidth = measuredHeight * this.mAdWidth / this.mAdHeight;
            }
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    public double getPlayheadTime() {
        if (isInPlaybackState()) {
            return this.mediaPlayer.getCurrentPosition();
        }
        return -1.0D;
    }

    public double getDuration() {
        if (isInPlaybackState()) {
            if (this.mDuration > 0) {
                return this.mDuration;
            }
            this.mDuration = this.mediaPlayer.getDuration();
            return this.mDuration;
        }
        this.mDuration = -1;
        return this.mDuration;
    }

    private void release(boolean cleartargetstate) {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.reset();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
            this.mCurrentState = VideoState.IDLE;
            if (cleartargetstate) {
                this.mTargetState = VideoState.IDLE;
            }
        }
    }

    protected boolean isInPlaybackState() {
        return (this.mediaPlayer != null) && (this.mCurrentState != VideoState.ERROR)
                && (this.mCurrentState != VideoState.IDLE) && (this.mCurrentState != VideoState.PREPARING)
                && (!this.preloading);
    }

    public void seekTo(int msec) {
        if (isInPlaybackState()) {
            this.mediaPlayer.seekTo(msec);
            this.mSeekWhenPrepared = 0;
        } else {
            this.mSeekWhenPrepared = msec;
        }
    }

    protected void onImprTimer() {
        int currentVolume = Math.round(this.audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM) * volumeScale);
        // mAdLogger.v(TAG, "current volume " + currentVolume);
        if (this.isMuted == MuteState.MUTED) {
            if (currentVolume > 0) {
                this.hostRenderer.onAdUnMuted();
            }
        } else if ((this.isMuted == MuteState.UNMUTED) && (currentVolume == 0)) {
            this.hostRenderer.onAdMuted();
        }

        this.isMuted = (currentVolume > 0 ? MuteState.UNMUTED : MuteState.MUTED);

        // TODO
        // FIXME
//        if (this.videoAdController != null) {
//            this.videoAdController.onImprTimer();
//        }
    }

    protected void loadContent() {
        Map<String, Object> errMap = new HashMap<String, Object>();

        this.mediaPlayer = new MediaPlayer();
        try {
            this.doCreativeLoading4MediaPlayer();
            this.mCurrentState = VideoState.PREPARING;
            this.preloading = true;
        } catch (IllegalArgumentException e) {
            this.mCurrentState = VideoState.ERROR;
            this.mTargetState = VideoState.ERROR;
            //
            errMap.put("INFO_KEY_ERROR_CODE", "ERROR_INVALID_VALUE");
            errMap.put("INFO_KEY_ERROR_INFO", e.getMessage());
            this.hostRenderer.onAdVideoViewError(errMap);
        } catch (IOException e) {
            this.mCurrentState = VideoState.ERROR;
            this.mTargetState = VideoState.ERROR;
            //
            errMap.put("INFO_KEY_ERROR_CODE", "ERROR_IO");
            errMap.put("INFO_KEY_ERROR_INFO", "Unable to open content: " + this.adUrl + ", error: " + e.toString());
            this.hostRenderer.onAdVideoViewError(errMap);
        }
    }

    public void setVolume(float pVolume) {
        this.mVolume = pVolume;
        volumeScale = pVolume;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(mVolume, mVolume);
        }
    }

    public void resize(int width, int height) {
        if ((this.mCurrentState == VideoState.PLAYING) || (this.mCurrentState == VideoState.PAUSED)) {
            this.mAdWidth = width;
            this.mAdHeight = height;

            getHolder().setFixedSize(width, height);
        }
    }
    
    /**
     * default
     * 
     * @author shsun
     * 
     */
    private static enum MuteState {
        UNINITIALIZED, MUTED, UNMUTED;
    }
}
