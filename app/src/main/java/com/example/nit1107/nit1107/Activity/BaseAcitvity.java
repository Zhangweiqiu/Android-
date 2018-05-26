package com.example.nit1107.nit1107.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.nit1107.nit1107.Server.ServerHelp;


@SuppressLint("Registered")
public class BaseAcitvity extends AppCompatActivity {
    public static int Type =0;
    public static BaseAcitvity myActivity;

    @Override
    protected void onCreate(Bundle savedInsatnceState) {
        super.onCreate(savedInsatnceState);
        if(getClass().getSimpleName().equals("ChatlistActivity"))
        {
            Type =1;
        }
        else if(getClass().getSimpleName().equals("ChatActivity"))
        {
            Type = 2;
        }
        else if(getClass().getSimpleName().equals("ContentActivity"))
        {
            Type = 3;
        }

        myActivity = this;
        Log.d("LogInfo", "当前的活动：" + myActivity.getClass().getSimpleName());

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ServerHelp.disConnected();
    }


    public void updateUI(String info)
    {

    }
}
