package com.example.nit1107.nit1107.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nit1107.nit1107.Activity.MainActivity;
import com.example.nit1107.nit1107.R;


public class LoginFragment extends Fragment {

    private TextView signup;
    private EditText account;
    private EditText password;

    private Button submit;
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle saveInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_login_layout,container ,false);

        //初始化各个需要使用对控件
        signup = (TextView) view.findViewById(R.id.signup);
        account = (EditText) view.findViewById(R.id.account);
        password = (EditText) view.findViewById(R.id.password);
        submit = (Button) view.findViewById(R.id.login_submit);

        //各个控件的点击事件
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入注册界面
                ((MainActivity) getActivity()).startSignFragment();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNoneEmpty())
                {
                    ((MainActivity)getActivity()).OnLogin(account.getText().toString(),password.getText().toString());
                }
                else
                {
                    Toast.makeText(getContext(),"账号或密码为空，请重新输入",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }



    private boolean isNoneEmpty()
    {
        return !(account.getText().toString().isEmpty() || password.getText().toString().isEmpty());
    }



}
