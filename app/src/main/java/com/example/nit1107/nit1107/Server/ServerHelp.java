package com.example.nit1107.nit1107.Server;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public  class ServerHelp {
    public static Socket socket;
    public static InputStream inputStream;
    public static InputStreamReader inputStreamReader;
    public static BufferedReader bufferedReader;
    public static OutputStream outputStream;


    public static void InitInput() throws IOException {
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
                    socket = new Socket("10.81.160.112",9999);
                    Log.e("JAVA","建立连接  " + socket);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
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
