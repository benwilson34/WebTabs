package edu.temple.webtabs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import drawable.TabFragment;

public class MainActivity extends FragmentActivity implements TabFragment.OnFragmentInteractionListener {

    TextView textView;
    Button bPrev, bNext, bGo;
    int currentTab = -1;

    // data structure for "tabs"
    FragmentManager fm;
    FragmentArray fragArray;

    MyAdapter myAdapter;
    ViewPager myPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fm = getSupportFragmentManager();
        fragArray = new FragmentArray();
        myAdapter = new MyAdapter(fm, fragArray);

        myPager = (ViewPager)findViewById(R.id.tabFrame);
        myPager.setAdapter(myAdapter);
        myPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("webtabd", "onPageSelected pos="+position);
                currentTab = position;
                updateButtons();
//                TabFragment tab = (TabFragment)myAdapter.getItem(myPager.getCurrentItem());
//                tab.reloadPage();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Button bNew = (Button)findViewById(R.id.buttonNewTab);
        bNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewTab("");
                updateButtons();
            }
        });

        bPrev = (Button)findViewById(R.id.buttonPrev);
        bPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              // if there's a tab to the left, change to it
                if(hasPrev())
                    myPager.setCurrentItem(myPager.getCurrentItem() - 1);
                updateButtons();
            }
        });

        bNext = (Button)findViewById(R.id.buttonNext);
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasNext())
                    myPager.setCurrentItem(myPager.getCurrentItem() + 1);
                updateButtons();
            }
        });

        textView = (TextView)findViewById(R.id.editText);

        bGo = (Button)findViewById(R.id.buttonGo);
        bGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = textView.getText().toString();
                if(fragArray.getSize() > 0) {
                    int currentTab = myPager.getCurrentItem();
                    ((TabFragment) fragArray.getFragment(currentTab)).loadURL(url);
                }
            }
        });

        updateButtons();
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("webtabd", "onResume called.");
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent){
        handleIntent(intent);
    }

    public void handleIntent(Intent intent){
        Uri uri = intent.getData();
        Log.d("webtabd-intent", "checking intent. ="+(uri != null));

        if(uri != null){
            String url = uri.toString();
            Log.d("webtabd-intent", "url="+url);
            createNewTab(url);
        }
    }


    public void createNewTab(String url){
        currentTab = myAdapter.getCount();
        myAdapter.newTab(url);
        myPager.setCurrentItem(currentTab);
    }

    public void updateButtons(){
        bPrev.setEnabled(hasPrev());
        bNext.setEnabled(hasNext());

        boolean atLeastOneTab = fragArray.getSize() > 0;
        textView.setEnabled(atLeastOneTab);
        bGo.setEnabled(atLeastOneTab);
    }

    public boolean hasPrev(){
        return myPager.getCurrentItem() > 0;
    }

    public boolean hasNext(){
        return myPager.getCurrentItem() < myAdapter.getCount() - 1;
    }

    @Override
    public void onFragmentInteraction(String newUrl) {
        textView.setText(newUrl);
    }
}
