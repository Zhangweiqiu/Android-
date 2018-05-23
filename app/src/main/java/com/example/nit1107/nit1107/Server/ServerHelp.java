package com.example.nit1107.nit1107.Server;


import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.nit1107.nit1107.Activity.BaseAcitvity;
import com.example.nit1107.nit1107.Activity.ChatActivity;
import com.example.nit1107.nit1107.Activity.MainActivity;
import com.example.nit1107.nit1107.db.UserAccount;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public  class ServerHelp {
    private static Socket socket;
    private static InputStream inputStream;
    private static InputStreamReader inputStreamReader;
    private static BufferedReader bufferedReader;
    private static OutputStream outputStream;

    private static String ReceiveInfo;
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 1:
                    ChatActivity.updateMessages(ReceiveInfo,0);
                    Log.d("Nit-message", ReceiveInfo);
                    break;
                default:
                    break;
            }
        }
    };

    private static void InitInput() throws IOException {
        inputStream = socket.getInputStream();
        inputStreamReader = new InputStreamReader(inputStream);
        bufferedReader = new BufferedReader(inputStreamReader);
    }

    public static void conn()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                try{
                    socket = new Socket("123.207.120.199",9999);
                    Log.e("JAVA","建立连接  " + socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void send( final String info) {
        new Thread() {
            @Override
            public void run() {

                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("ToName", "邱张伟");
                    jsonObject.put("FromName", "刘恒");
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

    public static void get() {
        new Thread() {
            @Override
            public void run() {


                try {

//                    socket.setSoTimeout(10000);
                    while (true)
                    {
                        ServerHelp.InitInput();

                        if(( ReceiveInfo = ServerHelp.bufferedReader.readLine())!=null)
                        {
                            JSONObject object = new JSONObject(ReceiveInfo);
                            String ToName = (String) object.get("ToName");
                            String FromName = (String) object.get("FromName");
                            ReceiveInfo = (String) object.get("content");
                            if(BaseAcitvity.Type == 1)
                            {
                                Log.d("ReceiveInfo", "ChatlistActivity 在接受消息 ");
                            }
                            else if(BaseAcitvity.Type == 2)
                            {
                                Log.d("ReceiveInfo", "ChatActivity 在接受消息 ");

//                                userAccounts = DataSupport.where("account = ?" ,FromName).find(UserAccount.class);
                                if (MainActivity.myCount.equals(ToName)) {
                                    Log.d("Nit-get", "input != null");
//                                    Message message = new Message();
//                                    message.what = 1;
                                    BaseAcitvity.myActivity.updateUI(ReceiveInfo);


                                }
                            }

                        }
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public static void closeInputStream() throws IOException {
        inputStream.close();
        inputStreamReader.close();
        bufferedReader.close();

    }

    public static void closeOutputStream() throws IOException {
        outputStream.close();
    }

    public static void closeSocket() throws IOException {
        socket.close();
    }

    public static void closeAll() throws IOException {
        closeSocket();
        closeInputStream();
        closeOutputStream();
    }
}
