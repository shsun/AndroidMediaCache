package com.shsunframework.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shsun on 17/1/12.
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = "AbstractBasicFragment";

    // by ms
    protected int mTimeoutOfHttpRequest = 1000 * 5;

    protected View mRootView;
    public Context mContext;

    protected boolean isUIViewVisible;
    private boolean isUIViewControllerCreated;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = initView(getArguments(), inflater, container, savedInstanceState);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.isUIViewControllerCreated = true;
        this.lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isUIViewVisible = getUserVisibleHint();
        if (this.isUIViewVisible) {
            this.onVisible();
        } else {
            this.onInvisible();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    protected void onVisible() {
        this.lazyLoad();
    }

    protected void onInvisible() {

    }

    /**
     * lazy loading
     */
    protected void lazyLoad() {
        if (this.isUIViewControllerCreated && this.isUIViewVisible) {
            this.initData(getArguments());
        }
    }

    /**
     * creeate UIViewComponent here
     *
     * @return
     */
    public abstract View initView(Bundle bundle, LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState);

    /**
     * fetch data here
     */
    public abstract void initData(Bundle bundle);
}
