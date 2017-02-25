package com.shsunframework.widget;

import android.content.Context;
import android.util.AttributeSet;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by shsun on 17/2/25.
 */

public class BaseVitamioVideoView extends VideoView {
    public BaseVitamioVideoView(Context context) {
        super(context);
    }

    public BaseVitamioVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseVitamioVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = getDefaultSize(this.getVideoWidth(), widthMeasureSpec);
        int measuredHeight = getDefaultSize(this.getVideoHeight(), heightMeasureSpec);

        if ((this.getVideoWidth() > 0) && (this.getVideoHeight() > 0)) {
            if (this.getVideoWidth() * measuredHeight > measuredWidth * this.getVideoHeight()) {
                measuredHeight = measuredWidth * this.getVideoHeight() / this.getVideoWidth();
            } else if (this.getVideoWidth() * measuredHeight < measuredWidth * this.getVideoHeight()) {
                measuredWidth = measuredHeight * this.getVideoWidth() / this.getVideoHeight();
            }
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

}
