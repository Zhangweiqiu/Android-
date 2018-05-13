package com.example.nit1107.nit1107;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.io.*;
import java.net.Socket;

import com.example.nit1107.nit1107.db.UserAccount;

import org.litepal.crud.DataSupport;

import java.net.UnknownHostException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LoginFragment loginFragment;
    private SignFragment signFragment;
    private ForgotFragment forgotFragment;
    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginFragment = new LoginFragment();
        signFragment = new SignFragment();
        forgotFragment = new ForgotFragment();
        show = findViewById(R.id.myTitle);
        startLoginFragment();

        new Thread()
        {
            @Override
            public void run()
            {
                getServerInfo();

            }
        }.start();
    }
    public void getServerInfo()
    {

        try {
            Socket socket = new Socket("10.81.160.112", 30000);

            //获取输出流 向服务端发送信息
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            outputStream.write("我是客户端".getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            //输入流 获取服务端对消息
            socket.setSoTimeout(10000);
            String line;
            if((line= bufferedReader.readLine())!=null)
            {
                show.setText(line);
            }

            outputStream.close();

            bufferedReader.close();
            socket.close();
        } catch (UnknownHostException e)
        {
            Log.d("UnknownHost","来自服务器的数据");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("IOE","来自服务器的数据");

            e.printStackTrace();
        }
    }


    //初使到登录界面
    public void startLoginFragment() {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, loginFragment)
                .commit();
    }
    //进入到注册界面
    public void startSignFragment()
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, signFragment)
                .commit();
    }

    //进入忘记密码界面

    public void startForgotFrament()
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, forgotFragment)
                .commit();
    }

    //登录事件 进入使用界面
    public void OnLogin(String account , String password)
    {
        boolean isgo = false;
        List<UserAccount> userAccounts = DataSupport.select("account","password").find(UserAccount.class);
        for(UserAccount userAccount : userAccounts)
        {
            if(account.equals(userAccount.getAccount() )&& password.equals(userAccount.getPassword()))
            {
                GoChatActivity();
                isgo =true;
                break;
            }
        }

        if(!isgo)
        {
            Toast.makeText(MainActivity.this,"账号或密码输入错误，请重新输入",Toast.LENGTH_SHORT).show();
        }
    }


    //注册事件 进入登录界面
    public void OnSign(String name , String account , String password , String confirmPassword)
    {
        if(password.equals(confirmPassword))
        {
            if(isNoEqualAccount(account))
            {
                UserAccount userAccount = new UserAccount();
                userAccount.setName(name);
                userAccount.setAccount(account);
                userAccount.setPassword(password);
                userAccount.save();
                Toast.makeText(MainActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                startLoginFragment();
            }

        }
        else
        {
            Toast.makeText(MainActivity.this,"两次密码不一致，请确认后重新输入",Toast.LENGTH_SHORT).show();
        }


    }

    public boolean isNoEqualAccount(String signAccount)
    {
        List<UserAccount> userAccounts = DataSupport.select("account").find(UserAccount.class);
        for(UserAccount userAccount : userAccounts)
        {
            if(signAccount.equals(userAccount.getAccount()))
            {
                Toast.makeText(MainActivity.this,"账号已经被注册，请修改后重试",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }


    public void GoChatActivity()
    {
        Intent intent = new Intent("com.example.activitytest.ACTION_START");
        intent.addCategory("com.example.activitytest.My_category");
        startActivity(intent);
    }
}
