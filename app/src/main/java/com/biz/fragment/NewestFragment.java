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

import com.biz.adapter.PersonAdapter;
import com.biz.entry.PersonEntry;
import com.shsunframework.adapter.recyclerview.OnRecyclerViewItemListener;
import com.shsunframework.app.BaseFragment;
import com.shsunframework.app.QQX5BrowserActivity;
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
                Intent intent = new Intent(NewestFragment.this.getActivity(), QQX5BrowserActivity.class);
                intent.putExtra(QQX5BrowserActivity.QQ_X5_BROWSER_KEY_URL, "http://www.qq.com");
                intent.putExtra(QQX5BrowserActivity.QQ_X5_BROWSER_KEY_CACHE_MODE, WebSettings.LOAD_NO_CACHE);
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
                "$7.00", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item01.jpg"));
        mDatas.add(new PersonEntry("2", "Fried Baby Onions", "maple syrup, walnut salsa, sauce",
                "$11.00", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item02.jpg"));
        mDatas.add(new PersonEntry("3", "Fried Rice", "red onion, kale, puffed wild rice",
                "$10.00", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item03.jpg"));
        mDatas.add(new PersonEntry("4", "Beet Fries", "cilantro, raw beet, feta, sumac",
                "$9.00", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item04.jpg"));
        mDatas.add(new PersonEntry("5", "Sautéed Spaghetti", "garlic, poached egg, almonds",
                "$12.00", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item05.jpg"));
        mDatas.add(new PersonEntry("6", "Grape Toast", "red cabbage, sweet onion, beef",
                "$14.00", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item06.jpg"));
        mDatas.add(new PersonEntry("7", "Fresh Acorn Squash", "pumplin mole, pomegranate, seed",
                "$11.00", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item07.jpg"));
        mDatas.add(new PersonEntry("8", "Quad Burgers", "biscuits, bacon, honey butter",
                "$7.00", "Dinner", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item02.jpg"));
        mDatas.add(new PersonEntry("9", "The Mister Burger", "pepperoni, cheese, lettuce fries",
                "$16.00", "Dinner", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item02.jpg"));
        mDatas.add(new PersonEntry("10", "Deep Fried String Cheese", "dipped in a honey mustard"
                + " aioli", "$7.00", "Dinner", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item07.jpg"));
        mDatas.add(new PersonEntry("11", "Cheese Plate", "a bunch of different types of cheeses",
                "$16.00", "Dinner", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item07.jpg"));
        mDatas.add(new PersonEntry("12", "Pear and Jicama", "cheese, chardonnay vinaigrette",
                "$12.00", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item06.jpg"));
        mDatas.add(new PersonEntry("13", "The Caesar", "garlic dressing, egg, olive oil, walnut"
                + " breadcrumbs", "$10.00", "Dinner - Veggies, Grains, Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item01.jpg"));
        mDatas.add(new PersonEntry("14", "Cold Brussels Sprouts", "shaved, raisins", "$10.00",
                "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item03.jpg"));
        mDatas.add(new PersonEntry("15", "Deep Fried Brussels Sprouts", "smoked yogurt and tea",
                "$12.00", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item04.jpg"));
        mDatas.add(new PersonEntry("16", "Bread & Whipped Cream", "bread with whipped cream",
                "$3.00", "Dinner - Salads", "https://raw.githubusercontent.com/shsun/AndroidMediaCache/master/app/src/main/res/drawable-hdpi/item05.jpg"));
    }



}