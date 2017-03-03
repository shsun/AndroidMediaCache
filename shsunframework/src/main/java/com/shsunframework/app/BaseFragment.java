package com.shsunframework.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shsun on 17/1/12.
 */
public abstract class BaseFragment extends Fragment {

    public String TAG = BaseFragment.class.getSimpleName();


    // by ms
    protected int mTimeoutOfHttpRequest = 1000 * 5;

    protected View mRootView;
    public Context mContext;

    protected boolean isUIViewVisible;
    private boolean isUIViewControllerCreated;



    @Override
    public void onAttach(Context context) {
        TAG = this.getClass().getSimpleName();

        Log.i(TAG, "before onAttach");
        super.onAttach(context);
        Log.i(TAG, "after onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "before onCreate");
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
        Log.i(TAG, "after onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        if (mRootView == null) {
            mRootView = initView(getArguments(), inflater, container, savedInstanceState);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "before onActivityCreated");

        super.onActivityCreated(savedInstanceState);
        this.isUIViewControllerCreated = true;
        this.lazyLoad();
        Log.i(TAG, "after onActivityCreated");
    }

    @Override
    public void onStart() {

        Log.i(TAG, "before onStart");
        super.onStart();
        Log.i(TAG, "after onStart");
    }

    @Override
    public void onResume() {
        Log.i(TAG, "before onResume");

        super.onResume();
        Log.i(TAG, "after onResume");
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
        Log.i(TAG, "before onPause");

        super.onPause();
        Log.i(TAG, "after onPause");
    }


    @Override
    public void onStop() {

        Log.i(TAG, "before onStop");

        super.onStop();
        Log.i(TAG, "after onStop");
    }

    @Override
    public void onDestroyView() {

        Log.i(TAG, "before onDestroyView");

        super.onDestroyView();
        Log.i(TAG, "after onDestroyView");
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "before onDestroy");

        super.onDestroy();
        Log.i(TAG, "after onDestroy");
    }


    @Override
    public void onDetach() {

        Log.i(TAG, "before onDetach");
        super.onDetach();
        Log.i(TAG, "after onDetach");
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
