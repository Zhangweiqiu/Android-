package com.example.nit1107.nit1107;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private LoginFragment loginFragment;
    private SignFragment signFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginFragment = new LoginFragment();
        signFragment = new SignFragment();
        startLoginFragment();

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

    }

    //登录事件 进入使用界面
    public void OnLogin(String account , String password)
    {

    }


    //注册事件 进入登录界面或者直接进入使用界面
    public void OnSign(String name , String account , String password , String confirmPassword)
    {

    }

}
