package com.example.nit1107.nit1107;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nit1107.nit1107.Adapter.ChatListAdapter;

/**
 * Created by qiuzhangwi on 2018/5/15.
 */

public class NewsFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle saveInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_news_layout,container ,false);

        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        tabLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60));
        //tab可滚动
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //tab居中显示
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        for (int i = 1;i < 3 ;i++){
            tabLayout.addTab(tabLayout.newTab().setText("刘恒"));
        }
        return view;
    }
}
