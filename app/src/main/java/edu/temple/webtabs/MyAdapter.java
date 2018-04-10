package edu.temple.webtabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import drawable.TabFragment;

/**
 * Created by Ben on 11/14/2017.
 */

public class MyAdapter extends FragmentStatePagerAdapter {

    FragmentArray fragArray;
    FragmentManager fm;

    public MyAdapter(FragmentManager fm, FragmentArray fragArray) {
        super(fm);
        this.fm = fm;
        this.fragArray = fragArray;
    }

    public void newTab(String url) {
        TabFragment tab = TabFragment.newInstance(url);
        fragArray.add(tab);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("webtabd", "Getting tab at "+position);
        TabFragment tab = (TabFragment)fragArray.getFragment(position);
        return tab;
    }

    @Override
    public int getCount() {
        return fragArray.getSize();
    }
}
