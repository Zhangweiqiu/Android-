package com.example.nit1107.nit1107.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nit1107.nit1107.NewsListFragment;
import com.example.nit1107.nit1107.R;

/**
 * Created by qiuzhangwi on 2018/5/16.
 */

public class NewsFragment extends Fragment{

    ViewPager viewPager;

    NewsListFragment mFragment1;

    NewsListFragment mFragment2;

    NewsListFragment mFragment3;

    PagerAdapter pagerAdapter;

    private TabLayout tabLayout;

    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle saveInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_news_layout,container ,false);
        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(2);
        tabLayout = view.findViewById(R.id.toolbar_tab);

        if (saveInstanceState == null) {
            mFragment1 = new NewsListFragment();
            mFragment1.init(0);
            mFragment2 = new NewsListFragment();
            mFragment2.init(1);
            mFragment3 = new NewsListFragment();
            mFragment3.init(2);
        }

        pagerAdapter = new PagerAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        return view;
    }

    public class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return mFragment1;
            } else if (position == 1) {
                return mFragment2;
            }else if (position == 2) {
                return mFragment3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

    }

}
