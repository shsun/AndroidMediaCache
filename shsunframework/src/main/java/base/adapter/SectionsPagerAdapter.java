package base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * viewPage adapter
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    protected String[] mTitles;
    protected List<Fragment> mFragments;

    /**
     * @param fm
     * @param fragments
     * @param titles
     */
    public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragments, String... titles) {
        super(fm);
        this.mTitles = titles;
        this.mFragments = fragments;
    }

    public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        this(fm, fragments, new String[]{});
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null && mTitles.length != 0) {
            return mTitles[position];
        } else {
            return mFragments.get(position).getTag().toLowerCase();
        }
    }
}