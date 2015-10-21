package com.aurorlin.v2ex;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.astuetz.PagerSlidingTabStrip;

public class MainActivity extends FragmentActivity {
    private PagerSlidingTabStrip tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabs.setViewPager(pager);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        private final String[] titles = {"最热", "最新"};
        private HotFragment hotFragment;
        private NewFragment newFragment;

        public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (hotFragment == null) {
                        hotFragment = new HotFragment();
                    }
                    return hotFragment;
                case 1:
                    if (newFragment == null) {
                        newFragment = new NewFragment();
                    }
                    return newFragment;
                default:
                    return null;
            }
        }
    }

}