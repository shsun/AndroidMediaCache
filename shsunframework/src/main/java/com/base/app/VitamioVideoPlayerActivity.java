package com.base.app;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.base.R;
import com.base.widget.VitamioMediaController;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 *
 */
public class VitamioVideoPlayerActivity extends Activity {

    public static final String TAG = "VitamioVideoPlayerActivity";

    public static final String VIDEO_PLAYER_KEY_URL = "VIDEO_PLAYER_KEY_URL";

    private static final int TIME = 0;
    private static final int BATTERY = 1;


    private VideoView mVideoView;
    private MediaController mMediaController;
    private VitamioMediaController mVitamioMediaController;
    private PopupWindow popupWindow;

    //    String vieoPath = "rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp";
    //String vieoPath = Environment.getExternalStorageDirectory() + "/b.mp4";



    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME:
                    mVitamioMediaController.setTime(msg.obj.toString());
                    break;
                case BATTERY:
                    mVitamioMediaController.setBattery(msg.obj.toString());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window = this.getWindow();
        window.setFlags(flag, flag);
        toggleHideyBar();
        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        //
        setContentView(R.layout.activity_vitamio_video_player);

        // data
        String vieoPath = this.getIntent().getStringExtra(VIDEO_PLAYER_KEY_URL);

        //
        mVideoView = (VideoView) findViewById(R.id.vitamio_video_view);
        mVideoView.setVideoPath(vieoPath);
        mMediaController = new MediaController(this);
        mVitamioMediaController = new VitamioMediaController(this, mVideoView, this);
        mVitamioMediaController.setOnItemClickListener(new VitamioMediaController.OnItemClickListener() {
            @Override
            public void itemClick(View view) {
                if (view.getId() == R.id.mediacontroller_quality1) {
                    qualityChange(view);
                }
                if (view.getId() == R.id.mediacontroller_play_next) {
                    VitamioVideoPlayerActivity.this.finish();
                }
            }
        });
        mMediaController.show(5000);
        //mVideoView.setMediaController(mMediaController);
        mVideoView.setMediaController(mVitamioMediaController);
        mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_MEDIUM);//画质 标清
        mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);
        //          VideoView.VIDEO_LAYOUT_ORIGIN;//原始画面
        //          VideoView.VIDEO_LAYOUT_STRETCH;//拉伸
        //          VideoView.VIDEO_LAYOUT_ZOOM;//裁剪
        //          VideoView.VIDEO_LAYOUT_SCALE;//全屏
        mVideoView.requestFocus();
        registerBoradcastReceiver();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    // 时间读取线程
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    String str = sdf.format(new Date());
                    Message msg = new Message();
                    msg.obj = str;
                    msg.what = TIME;
                    mHandler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // 当屏幕切换时 设置全屏
        if (mVideoView != null) {
            mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(batteryBroadcastReceiver);
        } catch (IllegalArgumentException ex) {
            ;
        }
    }

    private BroadcastReceiver batteryBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                //获取当前电量
                int level = intent.getIntExtra("level", 0);
                //电量的总刻度
                int scale = intent.getIntExtra("scale", 100);
                //把它转成百分比
                //tv.setText("电池电量为"+((level*100)/scale)+"%");
                Message msg = new Message();
                msg.obj = (level * 100) / scale + "";
                msg.what = BATTERY;
                mHandler.sendMessage(msg);
            }
        }
    };

    public void registerBoradcastReceiver() {
        //注册电量广播监听
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryBroadcastReceiver, intentFilter);

    }

    public void toggleHideyBar() {
        // BEGIN_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (get_current_ui_flags)
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        // END_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (toggle_ui_flags)
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i(TAG, "immersive mode mode off.");
        } else {
            Log.i(TAG, "immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        // 14
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // 16
        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        // 18
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        //END_INCLUDE (set_ui_flags)
    }

    private void qualityChange(View view) {
        View contentView = LayoutInflater.from(VitamioVideoPlayerActivity.this)
                .inflate(R.layout.ui_video_player_popuplayout, null);
        popupWindow = new PopupWindow(this);
        popupWindow.setContentView(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(view, Gravity.TOP, 0, -200);
    }
}
