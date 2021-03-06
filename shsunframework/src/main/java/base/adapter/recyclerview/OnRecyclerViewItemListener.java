package base.adapter.recyclerview;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author shsun
 *         <p>
 *         Created by shsun on 17/2/25.
 */
public interface OnRecyclerViewItemListener<T> {

    void onItemClick(ViewGroup parent, View view, T data, int position);

    boolean onItemLongClick(ViewGroup parent, View view, T data, int position);
}
