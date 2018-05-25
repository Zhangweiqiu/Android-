package com.example.nit1107.nit1107.Activity;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.nit1107.nit1107.Adapter.MsgAdapter;
import com.example.nit1107.nit1107.R;
import com.example.nit1107.nit1107.Server.ServerHelp;
import com.example.nit1107.nit1107.db.UserAccount;
import com.example.nit1107.nit1107.model.Msg;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class ChatActivity extends BaseAcitvity {

    private String friendName;

    private TextView title;

    private static List<Msg> msgList = new ArrayList<>();

    private static EditText inputText;

    private Button send;

    private static RecyclerView msgRecyclerView;

    private static MsgAdapter adapter;

    private Toolbar toolbar;

    private static Message message;
    private static String inputString;

    private List<UserAccount> userAccounts = new ArrayList<>();
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 1:
                    ChatActivity.updateMessages(inputString,0);
                    Log.d("Nit-message", inputString);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat_layout);

        friendName = getIntent().getStringExtra("friendName");
//        title = findViewById(R.id.friendName);
//        title.setText(friendName);


        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle(friendName);
        toolbar.setSubtitle("对方正在输入...");
        setSupportActionBar(toolbar);


        inputText =  findViewById(R.id.input_text);
        msgRecyclerView = findViewById(R.id.msg_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);

        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String content = inputText.getText().toString();
                    updateMessages(content,1);
                    ServerHelp.send(content);
                }
                return false;
            }
        });



        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
//        updateUI("nihao");

        //建立服务器连接
//        conn();

//        get();
        Log.d("-0-", "onCreate: chat");
    }

    public static void updateMessages(String content,int type)
    {
        if (!"".equals(content)){
            Msg msg = new Msg(content,type);
            msgList.add(msg);
            adapter.notifyItemInserted(msgList.size() - 1); //当有新消息时，刷新RecyclerView中的显示
            msgRecyclerView.scrollToPosition(msgList.size() - 1); //将RecyclerView定位到最后一行
            inputText.setText("");   //清空输入框中的内容
            Log.d("Nit-sucess", "update-sucess");

        }
    }








    private void initMsgs(){
        Msg msg1 = new Msg("Hello guy",Msg.TyPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello,Who is that?",Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("This is Tom .Nice talking to you",Msg.TyPE_RECEIVED);
        msgList.add(msg3);
    }

    /**
     * 该方法是用来加载菜单布局的
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载菜单文件
        getMenuInflater().inflate(R.menu.toolbar1, menu);
        return true;
    }
    @Override
    public void updateUI(String info)
    {
        inputString = info;
        message = new Message();
        message.what = 1;
        handler.sendMessage(message);
    }

    @Override
    public void onResume() {

        super.onResume();
        Log.d("OnResume", "onResume: ");
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_more:
                Toast.makeText(ChatActivity.this,"Click More", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

}
