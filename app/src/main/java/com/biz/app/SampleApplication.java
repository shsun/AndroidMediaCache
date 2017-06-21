package com.biz.app;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by shsun on 6/21/17.
 */

public class SampleApplication extends TinkerApplication {

    public SampleApplication() {
        super(
                // tinkerFlags, tinker支持的类型，dex,library，还是全部都支持！
                ShareConstants.TINKER_ENABLE_ALL,
                // ApplicationLike的实现类，只能传递字符串
                "com.biz.app.SampleApplicationLike",
                // Tinker的加载器，一般来说用默认的即可
                "com.tencent.tinker.loader.TinkerLoader",
                // tinkerLoadVerifyFlag, 运行加载时是否校验dex与,ib与res的Md5
                false);
    }
}