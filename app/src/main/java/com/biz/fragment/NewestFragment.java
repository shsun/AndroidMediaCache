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

import com.base.app.PDFActivity;
import com.biz.adapter.PersonAdapter;
import com.biz.entry.PersonEntry;
import com.base.adapter.recyclerview.OnRecyclerViewItemListener;
import com.base.app.BaseFragment;
import com.base.app.QQX5BrowserActivity;
import com.tencent.smtt.sdk.WebSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shsun on 17/1/12.
 */
public class NewestFragment extends BaseFragment {

    public static final String TAG = NewestFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private List<PersonEntry> mDatas = new ArrayList<PersonEntry>();
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

        addMenuItems();

        mAdapter = new PersonAdapter(this.getContext(), this.mDatas);
        mAdapter.setOnRecyclerViewItemListener(new OnRecyclerViewItemListener<PersonEntry>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, PersonEntry data, int position) {
                Log.i(TAG, "onItemClick=" + position + ", " + data.getName());

                Intent intent;
                if (position % 2 == 0) {
                    intent = new Intent(NewestFragment.this.getActivity(), QQX5BrowserActivity.class);
                    intent.putExtra(QQX5BrowserActivity.QQ_X5_BROWSER_KEY_URL, data.getLandingPageUrl());
                    intent.putExtra(QQX5BrowserActivity.QQ_X5_BROWSER_KEY_CACHE_MODE, WebSettings.LOAD_NO_CACHE);
                } else {
                    intent = new Intent(NewestFragment.this.getActivity(), PDFActivity.class);
                    intent.putExtra(PDFActivity.PDF_READER_KEY_URL, data.getLandingPageUrl());
                    intent.putExtra(PDFActivity.PDF_READER_KEY_TITLE, WebSettings.LOAD_NO_CACHE);
                }
                NewestFragment.this.getActivity().startActivity(intent);
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

    /**
     * Adds {@link PersonEntry}'s to the items list.
     */
    private void addMenuItems() {

        // Add the menu items.
        mDatas.add(new PersonEntry("1", "Cherry Avocado", "cherries, garlic, serrano, mayo",
                "http://www.263.com", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item01.jpg"));
        mDatas.add(new PersonEntry("2", "Fried Baby Onions", "maple syrup, walnut salsa, sauce",
                "http://news.qq.com", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item02.jpg"));
        mDatas.add(new PersonEntry("3", "Fried Rice", "red onion, kale, puffed wild rice",
                "http://news.qq.com/a/20170323/023149.htm", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item03.jpg"));
        mDatas.add(new PersonEntry("4", "Beet Fries", "cilantro, raw beet, feta, sumac",
                "http://news.qq.com/a/20170322/044117.htm", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item04.jpg"));
        mDatas.add(new PersonEntry("5", "Sautéed Spaghetti", "garlic, poached egg, almonds",
                "http://news.qq.com/a/20170324/027284.htm", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item05.jpg"));
        mDatas.add(new PersonEntry("6", "Grape Toast", "red cabbage, sweet onion, beef",
                "http://news.qq.com/a/20170324/030505.htm?pacclick=%2Fpac%2Fopenapi%3Fid%3D129", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item06.jpg"));
        mDatas.add(new PersonEntry("7", "Fresh Acorn Squash", "pumplin mole, pomegranate, seed",
                "http://news.qq.com/a/20170324/030505.htm?pacclick=%2Fpac%2Fopenapi%3Fid%3D129", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item07.jpg"));
        mDatas.add(new PersonEntry("8", "Quad Burgers", "biscuits, bacon, honey butter",
                "http://news.qq.com/a/20170324/030505.htm?pacclick=%2Fpac%2Fopenapi%3Fid%3D129", "Dinner", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item02.jpg"));
        mDatas.add(new PersonEntry("9", "The Mister Burger", "pepperoni, cheese, lettuce fries",
                "http://news.qq.com/a/20170324/030505.htm?pacclick=%2Fpac%2Fopenapi%3Fid%3D129", "Dinner", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item02.jpg"));
        mDatas.add(new PersonEntry("10", "Deep Fried String Cheese", "dipped in a honey mustard"
                + " aioli", "http://news.qq.com/a/20170324/030505.htm?pacclick=%2Fpac%2Fopenapi%3Fid%3D129", "Dinner", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item07.jpg"));
        mDatas.add(new PersonEntry("11", "Cheese Plate", "a bunch of different types of cheeses",
                "http://news.qq.com/a/20170324/030505.htm?pacclick=%2Fpac%2Fopenapi%3Fid%3D129", "Dinner", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item07.jpg"));
        mDatas.add(new PersonEntry("12", "Pear and Jicama", "cheese, chardonnay vinaigrette",
                "http://fashion.qq.com/a/20170324/003751.htm#p=1", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item06.jpg"));
        mDatas.add(new PersonEntry("13", "The Caesar", "garlic dressing, egg, olive oil, walnut"
                + " breadcrumbs", "http://fashion.qq.com/a/20170324/003742.htm", "Dinner - Veggies, Grains, Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item01.jpg"));
        mDatas.add(new PersonEntry("14", "Cold Brussels Sprouts", "shaved, raisins", "http://fashion.qq.com/a/20170324/003742.htm",
                "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item03.jpg"));
        mDatas.add(new PersonEntry("15", "Deep Fried Brussels Sprouts", "smoked yogurt and tea",
                "http://fashion.qq.com/a/20170324/003742.htm", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item04.jpg"));
        mDatas.add(new PersonEntry("16", "Bread & Whipped Cream", "bread with whipped cream",
                "http://www.baidu.com", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item05.jpg"));
    }



}