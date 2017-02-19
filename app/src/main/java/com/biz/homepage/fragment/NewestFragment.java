package com.biz.homepage.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.biz.homepage.PersonAdapter;
import com.biz.entry.PersonEntry;
import com.shsunframework.app.BaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by shsun on 17/1/12.
 */
public class NewestFragment extends BaseFragment {

    private static final String TAG = "NewestFragment";

    private RecyclerView mRecyclerView;
    private List<PersonEntry> mRecyclerViewItems = new ArrayList<PersonEntry>();

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
        addNativeExpressAds();
        setUpAndLoadNativeExpressAds();

        PersonAdapter adapter = new PersonAdapter(this.getContext(), this.mRecyclerViewItems);
        adapter.setOnRecyclerViewListener(new PersonAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position, PersonEntry entry) {
                Log.i(TAG, "onItemClick=" + position + ", " + entry.getName());
            }

            @Override
            public boolean onItemLongClick(int position, PersonEntry entry) {
                Log.i(TAG, "onItemLongClick=" + position + ", " + entry.getName());
                return false;
            }

        });
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * Adds {@link PersonEntry}'s to the items list.
     */
    private void addMenuItems() {

        // Add the menu items.
        mRecyclerViewItems.add(new PersonEntry("1", "Cherry Avocado", "cherries, garlic, serrano, mayo",
                "$7.00", "Dinner - Salads", "menu_item_image"));
        mRecyclerViewItems.add(new PersonEntry("2", "Fried Baby Onions", "maple syrup, walnut salsa, sauce",
                "$11.00", "Dinner - Salads", "menu_item_image"));
        mRecyclerViewItems.add(new PersonEntry("3", "Fried Rice", "red onion, kale, puffed wild rice",
                "$10.00", "Dinner - Salads", "menu_item_image"));
        mRecyclerViewItems.add(new PersonEntry("4", "Beet Fries", "cilantro, raw beet, feta, sumac",
                "$9.00", "Dinner - Salads", "menu_item_image"));
        mRecyclerViewItems.add(new PersonEntry("5", "Sautéed Spaghetti", "garlic, poached egg, almonds",
                "$12.00", "Dinner - Salads", "menu_item_image"));
        mRecyclerViewItems.add(new PersonEntry("6", "Grape Toast", "red cabbage, sweet onion, beef",
                "$14.00", "Dinner - Salads", "menu_item_image"));
        mRecyclerViewItems.add(new PersonEntry("7", "Fresh Acorn Squash", "pumplin mole, pomegranate, seed",
                "$11.00", "Dinner - Salads", "menu_item_image"));
        mRecyclerViewItems.add(new PersonEntry("8", "Quad Burgers", "biscuits, bacon, honey butter",
                "$7.00", "Dinner", "menu_item_image"));
        mRecyclerViewItems.add(new PersonEntry("9", "The Mister Burger", "pepperoni, cheese, lettuce fries",
                "$16.00", "Dinner", "menu_item_image"));
        mRecyclerViewItems.add(new PersonEntry("10", "Deep Fried String Cheese", "dipped in a honey mustard"
                + " aioli", "$7.00", "Dinner", "menu_item_image"));
        mRecyclerViewItems.add(new PersonEntry("11", "Cheese Plate", "a bunch of different types of cheeses",
                "$16.00", "Dinner", "menu_item_image"));
        mRecyclerViewItems.add(new PersonEntry("12", "Pear and Jicama", "cheese, chardonnay vinaigrette",
                "$12.00", "Dinner - Salads", "menu_item_image"));
        mRecyclerViewItems.add(new PersonEntry("13", "The Caesar", "garlic dressing, egg, olive oil, walnut"
                + " breadcrumbs", "$10.00", "Dinner - Veggies, Grains, Salads", "menu_item_image"));
        mRecyclerViewItems.add(new PersonEntry("14", "Cold Brussels Sprouts", "shaved, raisins", "$10.00",
                "Dinner - Salads", "menu_item_image"));
        mRecyclerViewItems.add(new PersonEntry("15", "Deep Fried Brussels Sprouts", "smoked yogurt and tea",
                "$12.00", "Dinner - Salads", "menu_item_image"));
        mRecyclerViewItems.add(new PersonEntry("16", "Bread & Whipped Cream", "bread with whipped cream",
                "$3.00", "Dinner - Salads", "menu_item_image"));
    }

    private void addNativeExpressAds() {
//        for (int i = 0; i <= mRecyclerViewItems.size(); i += ITEMS_PER_AD) {
//            WebView webview = new XMyWebView(this, true, true);
//            WebSettings settings = webview.getSettings();
//            settings.setJavaScriptEnabled(true);
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//                WebView.setWebContentsDebuggingEnabled(true);
//            }
//            mRecyclerViewItems.add(i, webview);
//        }
    }

    private void setUpAndLoadNativeExpressAds() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                /*
                final float density = MainActivity.this.getResources().getDisplayMetrics().density;
                // Set the ad size and ad unit ID for each Native Express ad in the items list.
                for (int i = 0; i <= mRecyclerViewItems.size(); i += ITEMS_PER_AD) {

                    final WebView adView =
                            (WebView) mRecyclerViewItems.get(i);
//                    adView.loadUrl("http://cpu.baidu.com/1001/d77e414");
                    String ad = "";
                    String fileName = "ad" + (Math.abs(new Random().nextInt() % 3)) + ".txt";
                    new Random().nextInt();
                    ad = ReadFromfile(fileName, MainActivity.this);

                    adView.loadDataWithBaseURL(null, getAdHtml(ad), "text/html", "UTF-8", null);
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(getScreenWidth(), 800);
                    adView.setLayoutParams(params);
                }
                */
            }
        });
    }

}