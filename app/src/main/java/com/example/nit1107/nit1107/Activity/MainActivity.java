package com.example.nit1107.nit1107.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nit1107.nit1107.Fragments.ForgotFragment;
import com.example.nit1107.nit1107.Fragments.SignFragment;
import com.example.nit1107.nit1107.LoginFragment;
import com.example.nit1107.nit1107.R;
import com.example.nit1107.nit1107.db.UserAccount;

import org.litepal.crud.DataSupport;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LoginFragment loginFragment;
    private SignFragment signFragment;

    public static  String myCount;
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

    //登录事件 进入使用界面
    public void OnLogin(String account , String password)
    {
        boolean isgo = false;
        List<UserAccount> userAccounts = DataSupport.select("account","password").find(UserAccount.class);
        for(UserAccount userAccount : userAccounts)
        {
            if(account.equals(userAccount.getAccount() )&& password.equals(userAccount.getPassword()))
            {
                myCount = account;
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
