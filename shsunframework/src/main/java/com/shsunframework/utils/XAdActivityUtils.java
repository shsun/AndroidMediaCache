package com.shsunframework.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.WindowManager;

public class XAdActivityUtils {

    /**
     * @param activity
     * @return fullscreen true indicate the Activity is in FULL_SCREEN state,
     * otherwise not.
     */
    public static Boolean isFullScreen(Activity activity) {
        try {
            if (activity != null) {
                final int v = activity.getWindow().getAttributes().flags;
                return (v & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN;
            }
            return false;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * @param activity
     * @param title
     * @param message
     * @param okDesc
     * @param cancelDesc
     * @param cancelable
     * @param positiveListener
     * @param negtiveListener
     */
    public static void showAlertDialog(Activity activity, String title,
                                String message, String okDesc, String cancelDesc,
                                boolean cancelable,
                                DialogInterface.OnClickListener positiveListener,
                                DialogInterface.OnClickListener negtiveListener) {
        if (activity == null) {
            return;
        }
        try {
            AlertDialog alert = new AlertDialog.Builder(activity)
                    .setCancelable(cancelable).setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(okDesc, positiveListener)
                    .setNegativeButton(cancelDesc, negtiveListener).create();
            alert.show();
        } catch (Exception e) {
            ;
        }

    }
}
