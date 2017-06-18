package base.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;

/**
 * @author shsun
 *         <p>
 *         Created by shsun on 17/2/19.
 */
public abstract class HeaderAndFooterAdapter<H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H> {

    protected int mHeaderSize;
    protected int mFooterSize;

    /**
     * @return
     */
    public int getmFooterSize() {
        return mFooterSize;
    }

    /**
     * @param mFooterSize
     */
    public void setmFooterSize(int mFooterSize) {
        this.mFooterSize = mFooterSize;
    }

    /**
     * @return
     */
    public int getmHeaderSize() {
        return mHeaderSize;
    }

    /**
     * @param mHeaderSize
     */
    public void setmHeaderSize(int mHeaderSize) {
        this.mHeaderSize = mHeaderSize;
    }
}