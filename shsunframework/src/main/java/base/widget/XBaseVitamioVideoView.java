package base.widget;

import android.content.Context;
import android.util.AttributeSet;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by shsun on 17/2/25.
 */

public class XBaseVitamioVideoView extends VideoView {

    public XBaseVitamioVideoView(Context context) {
        super(context);
    }

    public XBaseVitamioVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XBaseVitamioVideoView(Context context, AttributeSet attrs, int defStyle) {
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
