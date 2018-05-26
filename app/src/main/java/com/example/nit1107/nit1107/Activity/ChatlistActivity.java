package com.example.nit1107.nit1107.Activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.example.nit1107.nit1107.Fragments.ChatListFragment;
import com.example.nit1107.nit1107.Fragments.NewsFragment;
import com.example.nit1107.nit1107.QQNaviView;
import com.example.nit1107.nit1107.R;
import com.example.nit1107.nit1107.Server.ServerHelp;
import com.example.nit1107.nit1107.db.UserAccount;
import com.example.nit1107.nit1107.model.Friend;


import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.Toast.*;
//TabLayout.OnTabSelectedListene
public class ChatlistActivity extends BaseAcitvity {

    private DrawerLayout drawerLayout;

    private ChatListFragment chatListFragment;

    private  Toolbar toolbar;

    private NewsFragment newsFragment = new NewsFragment();;

    public static List<Friend> friendList = new ArrayList<>();


    private QQNaviView mBubbleView;
    private QQNaviView mStarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);

        chatListFragment = new ChatListFragment();

        //连接服务器
        ServerHelp.connect();
        //初始化
        initFriend();

        startChatListFragment();

        //上端导航
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("联系人");
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        //新的底部菜单栏
        mBubbleView = (QQNaviView) findViewById(R.id.qq_view_bubble);
        mStarView = (QQNaviView) findViewById(R.id.qq_view_star);
        mBubbleView.setBigIcon(R.drawable.bubble_big);
        mBubbleView.setSmallIcon(R.drawable.bubble_small);

        //个人基本信息
        List<UserAccount> userAccounts = DataSupport.where("account = ?" ,MainActivity.myCount).find(UserAccount.class);
        UserAccount userAccount = userAccounts.get(0);
        //右滑菜单
        NavigationView navigationView = findViewById(R.id.nav_view);
        View view = navigationView.inflateHeaderView(R.layout.nav_header);//headerLayout
        CircleImageView circleImageView = view.findViewById(R.id.icon_image);
        circleImageView.setImageResource(R.drawable.chat);
        TextView textView = view.findViewById(R.id.mail);
        textView.setText(userAccount.getAccount());
        TextView textView1 = view.findViewById(R.id.username);
        textView1.setText(userAccount.getName());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });

    }
    public void onClick(View view){
        resetIcon();
        switch (view.getId()){
            case R.id.qq_view_bubble:
                mBubbleView.setBigIcon(R.drawable.bubble_big);
                mBubbleView.setSmallIcon(R.drawable.bubble_small);

                chatListFragment = new ChatListFragment();
                toolbar.setTitle("联系人");
                setSupportActionBar(toolbar);
                startChatListFragment();

                break;
            case R.id.qq_view_star:
                mStarView.setBigIcon(R.drawable.star_big);
                mStarView.setSmallIcon(R.drawable.star_small);

                toolbar.setTitle("每天新");
                setSupportActionBar(toolbar);
                startNewsFragment();
                break;
        }
    }
    private void resetIcon() {
        mBubbleView.setBigIcon(R.drawable.pre_bubble_big);
        mBubbleView.setSmallIcon(R.drawable.pre_bubble_small);

        mStarView.setBigIcon(R.drawable.pre_star_big);
        mStarView.setSmallIcon(R.drawable.pre_star_small);
    }

    public void startNewsFragment()
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.chat_fragment_container, newsFragment)
                .commit();
    }
    public void startChatListFragment() {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.chat_fragment_container, chatListFragment)
                .commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                makeText(this,"1", LENGTH_SHORT).show();
                break;
            case R.id.delete:
                makeText(this,"2", LENGTH_SHORT).show();
                break;
            case R.id.settings:
                makeText(this,"3", LENGTH_SHORT).show();
                break;
            default:
        }

        return true;
    }



    //初始化聊天列表
    public static void initFriend(){

        for (int i=0;i<15;i++){
            Friend msgBean = new Friend();
            msgBean.setName("张某某"+i);
            msgBean.setContent("你好，在么？"+i);
            msgBean.setTime("上午10:30");
            if (i%5 == 0)
                msgBean.setHeadImg(R.drawable.head1);
            else if (i%5 == 1)
                msgBean.setHeadImg(R.drawable.head2);
            else if (i%5 == 2)
                msgBean.setHeadImg(R.drawable.head3);
            else if (i%5 == 3)
                msgBean.setHeadImg(R.drawable.head4);
            else if (i%5 == 4)
                msgBean.setHeadImg(R.drawable.head5);
            friendList.add(msgBean);
        }
    }


}


