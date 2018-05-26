package com.example.nit1107.nit1107.Activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.nit1107.nit1107.Adapter.MsgAdapter;
import com.example.nit1107.nit1107.R;
import com.example.nit1107.nit1107.Server.ServerHelp;
import com.example.nit1107.nit1107.model.Msg;

import java.util.ArrayList;
import java.util.List;



public class ChatActivity extends BaseAcitvity {

    private String friendName;

    private static List<Msg> msgList = new ArrayList<>();

    private static EditText inputText;

    private static RecyclerView msgRecyclerView;

    private static MsgAdapter adapter;


    private static Message message;
    private static String inputString;
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 1:
                    ChatActivity.updateMessages(inputString,0);
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
    }

    public static void updateMessages(String content,int type)
    {
        if (!"".equals(content)){
            Msg msg = new Msg(content,type);
            msgList.add(msg);
            adapter.notifyItemInserted(msgList.size() - 1); //当有新消息时，刷新RecyclerView中的显示
            msgRecyclerView.scrollToPosition(msgList.size() - 1); //将RecyclerView定位到最后一行
            inputText.setText("");   //清空输入框中的内容
            Log.d("LogInfo", "UI update success");

        }
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
