package com.example.nit1107.nit1107.Activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.nit1107.nit1107.Adapter.MsgAdapter;
import com.example.nit1107.nit1107.R;
import com.example.nit1107.nit1107.Server.ServerHelp;
import com.example.nit1107.nit1107.db.UserAccount;
import com.example.nit1107.nit1107.model.Msg;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;



public class ChatActivity extends BaseAcitvity {






    private String friendName;

    private TextView title;

    private List<Msg> msgList = new ArrayList<>();

    private EditText inputText;

    private Button send;

    private RecyclerView msgRecyclerView;

    private MsgAdapter adapter;

    private Toolbar toolbar;

    String inputString;

    private List<UserAccount> userAccounts = new ArrayList<>();
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 1:
                    sendMessages(inputString,0);
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
        title = findViewById(R.id.friendName);
        title.setText(friendName);

        initMsgs();
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        inputText =  findViewById(R.id.input_text);
        send =  findViewById(R.id.send);
        msgRecyclerView = findViewById(R.id.msg_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                sendMessages(content,1);
                send(content);
            }
        });


        //建立服务器连接
//        conn();

        get();
        Log.d("-0-", "onCreate: chat");
    }

    private void sendMessages(String content,int type)
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



    public void send( final String info) {
        new Thread() {
            @Override
            public void run() {

                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("ToName",MainActivity.myCount);
                    jsonObject.put("FromName", MainActivity.myCount);
                    jsonObject.put("content",info);
                    String infos = jsonObject.toString();
                    ServerHelp.outputStream = ServerHelp.socket.getOutputStream();
                    ServerHelp.outputStream.write((infos+"\n").getBytes("UTF-8"));
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void get() {
        new Thread() {
            @Override
            public void run() {


                try {

//                    socket.setSoTimeout(10000);
                    while (true)
                    {
                        ServerHelp.InitInput();
                        inputString = ServerHelp.bufferedReader.readLine();
                        if(inputString!=null)
                        {
                            Log.d("objecinfo", inputString);
                            JSONObject object = new JSONObject(inputString);
                            String ToName = (String) object.get("ToName");
                            String FromName = (String) object.get("FromName");
                            inputString = (String) object.get("content");
                            userAccounts = DataSupport.where("account = ?" ,FromName).find(UserAccount.class);
                            if (MainActivity.myCount.equals(ToName)) {
                                Log.d("Nit-get", "input != null");
                                Message message = new Message();

                                message.what = 1;
                                handler.sendMessage(message);
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initMsgs(){
        Msg msg1 = new Msg("Hello guy",Msg.TyPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello,Who is that?",Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("This is Tom .Nice talking to you",Msg.TyPE_RECEIVED);
        msgList.add(msg3);
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
    public void onDestroy()
    {
        super.onDestroy();
        try {
            ServerHelp.closeInputStream();
            ServerHelp.closeOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
