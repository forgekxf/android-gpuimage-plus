package com.bhtc.huajuan.push.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bhtc.huajuan.push.R;
import com.bhtc.huajuan.push.util.UIUtils;

import java.util.List;

/**
 * Created by kouxiongfei on 2017/5/18.
 */

public class ViewPagerAdapter  extends FragmentPagerAdapter {
    private String tabTitles[];
    private List<Fragment> mListFragment;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> mListFragment) {
        super(fm);
        this.mListFragment = mListFragment;
        tabTitles = new String[]{UIUtils.getString(R.string.user_info), UIUtils.getString(R.string.huajuan_info),UIUtils.getString(R.string.goods_info)};
    }

    @Override
    public Fragment getItem(int position) {
        return mListFragment.get(position);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
