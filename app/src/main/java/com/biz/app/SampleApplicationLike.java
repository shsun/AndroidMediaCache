package com.biz.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.biz.Constants;
import com.biz.log.MyLogImp;
import com.danikula.videocache.HttpProxyCacheServer;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;

import com.biz.util.SampleApplicationContext;
import com.biz.util.TinkerManager;

import java.io.File;

//@SuppressWarnings("unused")
//@DefaultLifeCycle(application = "com.biz.app.SampleApplication", flags = ShareConstants.TINKER_ENABLE_ALL)
public class SampleApplicationLike extends DefaultApplicationLike {

    private static final String TAG = "SampleApplicationLike";
    private HttpProxyCacheServer mProxyServer;

    public SampleApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag,
                                 long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    /**
     * install multiDex before install tinker
     * so we don't need to put the tinker lib classes in the main dex
     *
     * @param base
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        SampleApplicationContext.application = getApplication();
        SampleApplicationContext.context = getApplication();
        TinkerManager.setTinkerApplicationLike(this);

        TinkerManager.initFastCrashProtect();
        //should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true);

        //optional set logIml, or you can use default debug log
        TinkerInstaller.setLogIml(new MyLogImp());

        //installTinker after load multiDex
        //or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(this);
        Tinker tinker = Tinker.with(getApplication());

        Application application = this.getApplication();
        //
        com.xsj.crasheye.Crasheye.init(application, Constants.APP_KEY_OF_CRASHEYE);

        FLog.setMinimumLoggingLevel(FLog.VERBOSE);
        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "imgcache");
        DiskCacheConfig diskCfg = DiskCacheConfig.newBuilder(application)
                .setBaseDirectoryPath(file)
                .setBaseDirectoryName("czsccj")
                .setMaxCacheSize(200 * 1024 * 1024)//200MB
                .build();

        ImagePipelineConfig imgCfg = ImagePipelineConfig.newBuilder(application)
                .setMainDiskCacheConfig(diskCfg)
                .build();

        Fresco.initialize(application, imgCfg);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        Application application = this.getApplication();
        application.registerActivityLifecycleCallbacks(callback);
    }

    public HttpProxyCacheServer getProxyCacheServer() {
        if (mProxyServer == null) {
            // limit total count of files in cache:
//            mProxyServer = new HttpProxyCacheServer.Builder(this)
//                    .maxCacheFilesCount(20)
//                    .build();
            mProxyServer = new HttpProxyCacheServer.Builder(this.getApplication())
                    .maxCacheSize(1024 * 1024 * 1024)       // 100 M for cache
                    .build();
        }
        return mProxyServer;
    }

}
