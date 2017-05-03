package com.example.su.mygzzx.tablyout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by su
 * on 2017/4/21.
 */
public class TabfragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] titles = new String[]{"新帖", "热帖"};
    public TabfragmentAdapter(FragmentManager fm) {
        super(fm);
        init();
    }

    private void init() {
        fragmentList.add(new Fragmentone());
        fragmentList.add(new Fragmenttwo());
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
