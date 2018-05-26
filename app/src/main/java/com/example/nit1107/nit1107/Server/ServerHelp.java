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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public  class ServerHelp {
    private static Socket socket;
    private static boolean beConnnected = false;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;


    private final static String IP = "172.20.10.3";

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


    public static void connect()
    {
        try {
            socket = new Socket(IP,9999);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            new Thread(new ChatThread()).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void disConnected()
    {
        beConnnected = false;

        try {
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static class ChatThread implements Runnable{
        @Override
        public void run()
        {

            try {
                beConnnected = true;
                while (beConnnected)
                {
                    ReceiveInfo = dataInputStream.readUTF();
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
                        if ("刘恒".equals(ToName)) {
                            Log.d("Nit-get", "input != null");
//                                    Message message = new Message();
//                                    message.what = 1;
                            BaseAcitvity.myActivity.updateUI(ReceiveInfo);


                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


    public static void send( final String info) {

                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("ToName", "邱张伟");
                    jsonObject.put("FromName", "刘恒");
                    jsonObject.put("content",info);
                    String infos = jsonObject.toString();
                    dataOutputStream.writeUTF(infos);
                    dataOutputStream.flush();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
}
