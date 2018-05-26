package com.example.nit1107.nit1107.Server;


import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.nit1107.nit1107.Activity.BaseAcitvity;
import com.example.nit1107.nit1107.Activity.ChatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public  class ServerHelp {
    private static Socket socket;

    private static boolean beConnnected = false;

    private static DataInputStream dataInputStream;

    private static DataOutputStream dataOutputStream;

    private final static String IP = "123.207.120.119";

    private static String ReceiveInfo;


    public static void connect()
    {
          new Thread(new ChatThread()).start();

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
                socket = new Socket(IP,9999);
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
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
                        Log.d("LogInfo", "ChatlistActivity 在接受消息 ");
                    }
                    else if(BaseAcitvity.Type == 2)
                    {
                        Log.d("LogoInfo", "ChatActivity 在接受消息 ");

                        if ("邱张伟".equals(ToName)) {
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

        new Thread()
        {
            @Override
            public void run()
            {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("ToName", "刘恒");
                    jsonObject.put("FromName", "邱张伟");
                    jsonObject.put("content",info);
                    String infos = jsonObject.toString();
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeUTF(infos);
                    dataOutputStream.flush();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
