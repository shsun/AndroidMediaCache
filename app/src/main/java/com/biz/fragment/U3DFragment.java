package com.biz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ScottyB.JuggleThis.UnityPlayerNativeActivity;
import com.biz.adapter.PersonAdapter;
import com.biz.entry.PersonEntry;
import com.base.adapter.recyclerview.OnRecyclerViewItemListener;
import com.base.controller.XBaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by shsun on 17/1/12.
 */
public class U3DFragment extends XBaseFragment {

    public static final String TAG = U3DFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private List<PersonEntry> mRecyclerViewItems = new ArrayList<PersonEntry>();
    private PersonAdapter mAdapter;

    @Override
    public View initView(Bundle bundle, LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {

        mRecyclerView = new RecyclerView(this.getActivity());
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        return mRecyclerView;
    }

    @Override
    public void initData(Bundle bundle) {
        Toast.makeText(getActivity(), "加载了最新数据", Toast.LENGTH_SHORT).show();

        for (int i = 0; i < 5; i++) {
            mRecyclerViewItems.add(new PersonEntry(i + "", "Cherry Avocado__" + i, "cherries, garlic, serrano, mayo__" + i,
                    "$7.00", "Dinner - Salads_" + i, "menu_item_image_" + i));
        }

        mAdapter = new PersonAdapter(this.getContext(), this.mRecyclerViewItems);
        mAdapter.setOnRecyclerViewItemListener(new OnRecyclerViewItemListener<PersonEntry>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, PersonEntry data, int position) {
                Log.i(TAG, "onItemClick=" + position + ", " + data.getName());
                Intent intent = new Intent(U3DFragment.this.getActivity(), UnityPlayerNativeActivity.class);
                U3DFragment.this.getActivity().startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, PersonEntry data, int position) {
                Log.i(TAG, "onItemLongClick=" + position + ", " + data.getName());
                return false;
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void onInvisible() {

    }
}