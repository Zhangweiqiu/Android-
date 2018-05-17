package com.example.nit1107.nit1107.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nit1107.nit1107.R;

public class ForgotFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle saveInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_forgot_layout,container ,false);


        return view;

    }
}
