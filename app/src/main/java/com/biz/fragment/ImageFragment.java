package com.biz.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.biz.R;
import com.biz.adapter.ImageAdapter;
import com.shsunframework.app.BaseFragment;

import java.util.List;

import me.kaede.frescosample.ImageApi;

/**
 *
 */
public class ImageFragment extends BaseFragment {

    private int mIndex;

    // find view
    RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;


    /**
     * @param index
     */
    public ImageFragment(int index) {
        super();
        this.mIndex = index;
    }

    @Override
    public View initView(Bundle bundle, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        // init
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(mIndex + 1, StaggeredGridLayoutManager.VERTICAL);
        this.mRecyclerView.setLayoutManager(layoutManager);

        //
        this.mAdapter = new ImageAdapter(this.getContext(), R.layout.item_recyclerview);
        this.mRecyclerView.setHasFixedSize(false);
        this.mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void initData(Bundle bundle) {
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
        this.mAdapter.setDatas(datas);
    }
}
