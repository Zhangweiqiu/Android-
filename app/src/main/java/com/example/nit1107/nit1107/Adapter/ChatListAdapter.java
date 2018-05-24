package com.example.nit1107.nit1107.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nit1107.nit1107.R;
import com.example.nit1107.nit1107.model.Friend;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by qiuzhangwi on 2018/5/13.
 */

public class ChatListAdapter extends BaseAdapter {

    private Context context;
    private List<Friend> data;
    private LayoutInflater inflater;

    public ChatListAdapter(Context context, List<Friend> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holoder = null;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.head_item,null);
            holoder = new ViewHolder();
            holoder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holoder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holoder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            holoder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holoder);
        }
        else {
            holoder = (ViewHolder) convertView.getTag();
        }

        Friend item = data.get(position);
        holoder.tvName.setText(item.getName());
        holoder.tvTime.setText(item.getTime());
        holoder.tvContent.setText(item.getContent());
        return convertView;
    }

    public class ViewHolder{
        TextView tvName;
        TextView tvContent;
        TextView tvTime;
        ImageView ivIcon;
    }


}