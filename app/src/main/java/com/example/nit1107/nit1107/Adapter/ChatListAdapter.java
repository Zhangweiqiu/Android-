package com.example.nit1107.nit1107.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nit1107.nit1107.R;
import com.example.nit1107.nit1107.model.Friend;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by qiuzhangwi on 2018/5/13.
 */

public class ChatListAdapter extends ArrayAdapter<Friend> {

    private int resourceId;

    public ChatListAdapter(Context context, int textViewResourceId, List<Friend> object){
        super(context,textViewResourceId,object);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View converView , ViewGroup parent){
        Friend chat = getItem(position);  //获取当前项的聊天的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        CircleImageView chatImage = view.findViewById(R.id.chat_img);
        TextView chatName = view.findViewById(R.id.chat_name);
        TextView lastContent = view.findViewById(R.id.id_item_tv2);
        chatImage.setImageResource(chat.getImgId());
        chatName.setText(chat.getName());
        lastContent.setText(chat.getName());

        return view;
    }
}
