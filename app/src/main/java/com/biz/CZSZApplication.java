package com.biz;

import java.io.File;
import java.util.Stack;

import com.danikula.videocache.HttpProxyCacheServer;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.shsunframework.BaseApplication;

import android.app.Activity;
import android.app.Application;
import android.os.Environment;

/**
 *
 */
public class CZSZApplication extends BaseApplication {

    private HttpProxyCacheServer mProxyServer;


    private static CZSZApplication theSingletonInstance;

    // Returns the application instance
    public static CZSZApplication getInstance() {
        return theSingletonInstance;
    }

    @Override
    public void onCreate() {
        com.xsj.crasheye.Crasheye.init(this, "e0ce3280");

        super.onCreate();
        theSingletonInstance = this;

        //
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);
        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "imgcache");
        DiskCacheConfig diskCfg = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(file)
                .setBaseDirectoryName("czsccj")
                .setMaxCacheSize(200 * 1024 * 1024)//200MB
                .build();

        ImagePipelineConfig imgCfg = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCfg)
                .build();

        Fresco.initialize(this, imgCfg);

    }

    public HttpProxyCacheServer getProxyCacheServer() {
        if (mProxyServer == null) {
            // limit total count of files in cache:
//            mProxyServer = new HttpProxyCacheServer.Builder(this)
//                    .maxCacheFilesCount(20)
//                    .build();

            mProxyServer = new HttpProxyCacheServer.Builder(this)
                    .maxCacheSize(1024 * 1024 * 1024)       // 100 M for cache
                    .build();
        }
        return mProxyServer;
    }
}

