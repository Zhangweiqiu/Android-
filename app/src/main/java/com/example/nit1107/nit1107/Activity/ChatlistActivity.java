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
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.example.nit1107.nit1107.Fragments.ChatListFragment;
import com.example.nit1107.nit1107.Fragments.NewsFragment;
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
    private BottomNavigationBar bottomNavigationBar;

    private ChatListFragment chatListFragment;

    private TextBadgeItem textBadgeItem;

    private ShapeBadgeItem shapeBadgeItem;

    private ChatActivity chatFragment;

    private  Toolbar toolbar;

    private NewsFragment newsFragment = new NewsFragment();;

    public static List<Friend> friendList = new ArrayList<>();

    private List<UserAccount> userAccounts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);

        chatListFragment = new ChatListFragment();

        ServerHelp.conn();
        //初始化
        initFriend();

        startChatListFragment();

        //上端导航
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("NIT_CHAT");
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        //底部菜单栏
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        textBadgeItem = new TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.colorAccent);
        shapeBadgeItem = new ShapeBadgeItem()
                .setShapeColorResource(R.color.colorPrimary)
                .setGravity(Gravity.TOP|Gravity.END)
                .setHideOnSelect(false);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setBarBackgroundColor("#FFFFFF");
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.logo,"联系人"))
                .addItem(new BottomNavigationItem(R.drawable.logo,"新闻中心"))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position){
                    case 0:
                        chatListFragment = new ChatListFragment();
                        toolbar.setTitle("NIT_CHAT");
                        setSupportActionBar(toolbar);
                     //   newsFragment = new NewsFragment();
                        startChatListFragment();
                    case 1:
                        toolbar.setTitle("每天新");
                        setSupportActionBar(toolbar);
                        chatListFragment = new ChatListFragment();

                }
            }

            @Override
            public void onTabUnselected(int position) {
                    if (position == 0 ){
                        toolbar.setTitle("每天新");
                        setSupportActionBar(toolbar);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.chat_fragment_container, newsFragment)
                                .commit();
                    }else {
                        toolbar.setTitle("NIT_CHAT");
                        setSupportActionBar(toolbar);
                        startChatListFragment();
                   }
            }

            @Override
            public void onTabReselected(int position) {

            }
        });

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


        for (int i = 0 ; i < 7; i++) {

            Friend friend1 = new Friend("张三", R.drawable.ic_menu);
            friendList.add(friend1);
            Friend friend2 = new Friend("李四", R.drawable.ic_menu);
            friendList.add(friend2);
            Friend friend3 = new Friend("王五", R.drawable.ic_menu);
            friendList.add(friend3);
            Friend friend4 = new Friend("柳六", R.drawable.ic_menu);
            friendList.add(friend4);
            Friend friend5 = new Friend("延期", R.drawable.ic_menu);
            friendList.add(friend5);
        }
    }


}


