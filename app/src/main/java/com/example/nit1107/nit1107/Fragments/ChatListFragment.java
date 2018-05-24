package com.example.nit1107.nit1107.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nit1107.nit1107.Activity.ChatActivity;
import com.example.nit1107.nit1107.Activity.ChatlistActivity;
import com.example.nit1107.nit1107.Adapter.ChatListAdapter;
import com.example.nit1107.nit1107.R;
import com.example.nit1107.nit1107.model.Friend;
import com.hankkin.library.RefreshSwipeMenuListView;
import com.hankkin.library.SwipeMenu;
import com.hankkin.library.SwipeMenuCreator;
import com.hankkin.library.SwipeMenuItem;

import java.util.ArrayList;
import java.util.List;


public class ChatListFragment extends Fragment implements RefreshSwipeMenuListView.OnRefreshListener{
    private RefreshSwipeMenuListView rsmLv;
    private ChatListAdapter adapter;
    private List<Friend> data;
    private int po;
    public View onCreateView(final LayoutInflater inflater , ViewGroup container, Bundle saveInstanceState)
    {
        View view = inflater.inflate(R.layout.chatlist_layout,container ,false);

        rsmLv = view.findViewById(R.id.swipe);

        data =  new ArrayList<>();
        data = ChatlistActivity.friendList;
        adapter = new ChatListAdapter(getContext(),data);

        rsmLv.setAdapter(adapter);

        rsmLv.setListViewMode(RefreshSwipeMenuListView.HEADER);
        rsmLv.setOnRefreshListener(this);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // 创建滑动选项
                SwipeMenuItem rejectItem = new SwipeMenuItem(
                        getContext());
                // 设置选项背景
                rejectItem.setBackground(new ColorDrawable(getResources().getColor(R.color.top)));
                // 设置选项宽度
                rejectItem.setWidth(dp2px(80,getContext()));
                // 设置选项标题
                rejectItem.setTitle("置顶");
                // 设置选项标题
                rejectItem.setTitleSize(16);
                // 设置选项标题颜色
                rejectItem.setTitleColor(Color.WHITE);
                // 添加选项
                menu.addMenuItem(rejectItem);

                // 创建删除选项
                SwipeMenuItem argeeItem = new SwipeMenuItem(getContext());
                argeeItem.setBackground(new ColorDrawable(getResources().getColor(R.color.del)));
                argeeItem.setWidth(dp2px(80, getContext()));
                argeeItem.setTitle("删除");
                argeeItem.setTitleSize(16);
                argeeItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(argeeItem);
            }
        };

        rsmLv.setMenuCreator(creator);
        rsmLv.setOnMenuItemClickListener(new RefreshSwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0: //第一个选项
                        Toast.makeText(getContext(),"您点击的是置顶",Toast.LENGTH_SHORT).show();
                        break;
                    case 1: //第二个选项
                        del(position,rsmLv.getChildAt(position+1-rsmLv.getFirstVisiblePosition()));
                        break;

                }
            }
        });
        rsmLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Friend friend = data.get(i);
                Intent intent = new Intent(getContext(),ChatActivity.class);
                intent.putExtra("friendName",friend.getName());
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onRefresh() {
        rsmLv.postDelayed(new Runnable() {
            @Override
            public void run() {
                rsmLv.complete();
                Toast.makeText(getContext(),"已完成",Toast.LENGTH_SHORT).show();
            }
        },2000);
    }

    @Override
    public void onLoadMore() {
        rsmLv.postDelayed(new Runnable() {
            @Override
            public void run() {
                rsmLv.complete();
                Toast.makeText(getContext(),"已完成",Toast.LENGTH_SHORT).show();
            }
        },2000);

    }
    public  int dp2px(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * 删除item动画
     * @param index
     * @param v
     */
    private void del(final int index, View v){
        final Animation animation = (Animation) AnimationUtils.loadAnimation(v.getContext(), R.anim.list_anim);
        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
                data.remove(index);
                po = index;
                adapter.notifyDataSetChanged();
                animation.cancel();
            }
        });

        v.startAnimation(animation);
    }


}
