package com.example.nit1107.nit1107;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignFragment extends Fragment {

    public SignFragment()
    {

    }

    private EditText name;
    private EditText account;
    private EditText password;
    private EditText confirmPassword;
    private Button submit;
    private TextView login;



    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle saveInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_sign_layout,container ,false);

        name = (EditText) view.findViewById(R.id.name);
        account = (EditText) view.findViewById(R.id.account);
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirm_password);
        submit = (Button) view.findViewById(R.id.submit);
        login = (TextView) view.findViewById(R.id.login);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNoEmpty())
                {
                    ((MainActivity) getActivity()).OnSign(name.getText().toString(),account.getText().toString(),
                            password.getText().toString(),confirmPassword.getText().toString());
                }
                else
                {
                    Toast.makeText(getContext(),"某项信息为空，请重新确认",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    private boolean isNoEmpty()
    {
        return !(name.getText().toString().isEmpty()|| account.getText().toString().isEmpty()
                || password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty());
    }
}
