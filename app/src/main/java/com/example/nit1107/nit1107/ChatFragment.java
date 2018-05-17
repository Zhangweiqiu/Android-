package com.example.nit1107.nit1107;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
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
import com.example.nit1107.nit1107.db.UserAccount;
import com.example.nit1107.nit1107.model.Msg;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;



public class ChatFragment extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();

    private EditText inputText;

    private Button send;

    private RecyclerView msgRecyclerView;

    private MsgAdapter adapter;

    private Toolbar toolbar;

    String inputString;

    private List<UserAccount> userAccounts = new ArrayList<>();

    public static Socket socket;
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
        initMsgs();
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle("");
        TextView textView = findViewById(R.id.toolbar1_title);
        textView.setText("刘恒");
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
    public static void conn()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                try{
                    socket = new Socket("123.207.120.119",9999);
                    Log.e("JAVA","建立连接  " + socket);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public void send( final String info) {
        new Thread() {
            @Override
            public void run() {

                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("ToName",123);
                    jsonObject.put("FromName",MainActivity.myCount);
                    jsonObject.put("content",info);
                    String infos = jsonObject.toString();
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write((infos+"\n").getBytes("UTF-8"));

                    System.out.println("发送消息");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
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
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    socket.setSoTimeout(10000);
                    while (true)
                    {
                        inputString = bufferedReader.readLine();
                        Log.d("get-0-", inputString);

                        if(inputString!=null)
                        {
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

}
