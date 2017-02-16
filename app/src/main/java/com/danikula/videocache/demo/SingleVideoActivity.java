package com.danikula.videocache.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.biz.R;

import org.androidannotations.annotations.EActivity;



@EActivity(R.layout.activity_single_video)
public class SingleVideoActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        if (state == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.containerView, VideoFragment.build(this, Video.ORANGE_1))
                    .commit();
        }
    }
}
