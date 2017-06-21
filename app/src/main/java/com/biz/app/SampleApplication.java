package com.biz.app;

import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by shsun on 6/21/17.
 */

public class SampleApplication extends XBaseTinkerApplication {

    public SampleApplication() {
        super(
                // tinkerFlags, which types is supported
                // dex only, library only, all support
                ShareConstants.TINKER_ENABLE_ALL,
                // This is passed as a string so the shell application does not
                // have a binary dependency on your ApplicationLifeCycle class.
                "com.biz.app.SampleApplicationLike");
    }
}
