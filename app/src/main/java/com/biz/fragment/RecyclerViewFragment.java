package com.biz.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.biz.R;
import com.biz.adapter.RecyclerViewAdapter;
import com.shsunframework.app.BaseFragment;

import java.util.List;

import me.kaede.frescosample.ImageApi;

/**
 *
 */
public class RecyclerViewFragment extends BaseFragment {

    private int mIndex;

    // find view
    RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    /**
     * @param index
     */
    public RecyclerViewFragment(int index) {
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

        return view;
    }

    @Override
    public void initData(Bundle bundle) {
        //
        this.mAdapter = new RecyclerViewAdapter(mIndex);
        this.mRecyclerView.setAdapter(mAdapter);
        this.mRecyclerView.setHasFixedSize(false);

        List<String> datas = ImageApi.jk.getUrls();
        switch (mIndex) {
            case 0:
            default:
                datas = ImageApi.jk.getUrls();
                break;
            case 1:
                datas = ImageApi.girly.getUrls();
                break;
            case 2:
                datas = ImageApi.legs.getUrls();
                break;
        }
        mAdapter.setmList(datas);
    }

}
