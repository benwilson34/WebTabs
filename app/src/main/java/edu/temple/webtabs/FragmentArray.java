package edu.temple.webtabs;

import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;

public class FragmentArray {
    private ArrayList<Fragment> list;

    public FragmentArray(){
        list = new ArrayList<>();
    }

    public void add(Fragment frag){
        list.add(frag);
    }

    public Fragment getFragment(int pos){
        Log.d("webtabd", "getting fragment at "+pos);
        return list.get(pos);
    }

    public int getSize(){ return list.size(); }

}
