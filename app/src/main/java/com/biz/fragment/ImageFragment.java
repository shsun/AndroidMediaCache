package com.biz.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.biz.R;
import com.biz.adapter.ImageAdapter;
import com.shsunframework.adapter.recyclerview.OnRecyclerViewItemListener;
import com.shsunframework.app.BaseFragment;

import java.util.List;

import me.kaede.frescosample.ImageApi;

/**
 *
 */
public class ImageFragment extends BaseFragment {

    public static final String TAG = ImageFragment.class.getSimpleName();


    private int mIndex;

    private String mType;
    // find view
    RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private Handler mHandler;
    DataRefreshingRunnable mRefresher;


    public static ImageFragment newInstance(int index) {
        ImageFragment f = new ImageFragment();
        //f.setArguments(args);
        f.mIndex = index;
        return f;
    }

    @Override
    public View initView(Bundle bundle, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        // init
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(mIndex + 1, StaggeredGridLayoutManager.VERTICAL);
        this.mRecyclerView.setLayoutManager(layoutManager);
        this.mRecyclerView.setHasFixedSize(false);
//
        this.mAdapter = new ImageAdapter(this.getContext(), R.layout.item_pure_image_recyclerview);
        this.mAdapter.setOnRecyclerViewItemListener(new OnRecyclerViewItemListener<String>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, String data, int position) {
                mAdapter.remove(position);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, String data, int position) {
                return false;
            }
        });

        //
        return view;
    }




    @Override
    public void initData(Bundle bundle) {
        if(this.mRecyclerView.getAdapter() == null){
            List<String> datas = null;
            switch (mIndex) {
                case 0:
                    datas = ImageApi.jk.getUrls();
                    break;
                case 1:
                    datas = ImageApi.girly.getUrls();
                    break;
                case 2:
                    datas = ImageApi.legs.getUrls();
                    break;
                default:
                    datas = ImageApi.jk.getUrls();
                    break;
            }
            mAdapter.setDataProvider(datas);
            this.mRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.notifyDataSetChanged();
        }


        mRefresher = new DataRefreshingRunnable(mAdapter);
        mHandler = new Handler(this.getActivity().getMainLooper());
        mHandler.postDelayed(mRefresher, 1000*10);
    }


    @Override
    protected void onInvisible() {
        Log.d(TAG, "onInvisible");
        if(mRefresher != null){
            mHandler.removeCallbacks(mRefresher);
        }
    }

    class DataRefreshingRunnable implements Runnable {

        private ImageAdapter imageAdapter;

        public DataRefreshingRunnable(ImageAdapter adapter){
            imageAdapter = adapter;
        }
        @Override
        public void run() {
            List<String> t = ImageApi.girly.getUrls();
            imageAdapter.setDataProvider(t);
            imageAdapter.notifyDataSetChanged();
        }
    }
}
