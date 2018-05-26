package com.example.nit1107.nit1107.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nit1107.nit1107.R;
import com.example.nit1107.nit1107.model.Msg;

import java.util.List;

/**
 * Created by qiuzhangwi on 2018/5/13.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder>{

    private List<Msg> mMsgList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftLayout;

        LinearLayout rightLayout;

        TextView leftMsg;

        TextView rightMsg;

        de.hdodenhof.circleimageview.CircleImageView leftImg;

        de.hdodenhof.circleimageview.CircleImageView rightImg;

        public ViewHolder(View itemView) {
            super(itemView);
            this.leftLayout =  itemView.findViewById(R.id.left_layout);
            this.rightLayout =  itemView.findViewById(R.id.right_layout);
            this.leftMsg =  itemView.findViewById(R.id.left_msg);
            this.rightMsg =  itemView.findViewById(R.id.right_msg);
            this.leftImg = itemView.findViewById(R.id.head_left);
            this.rightImg = itemView.findViewById(R.id.head_right);
        }
    }
    public MsgAdapter(List<Msg> msgList){
        mMsgList = msgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Msg msg = mMsgList.get(position);
        if (msg.getType() == Msg.TyPE_RECEIVED){
            //如果是收到的消息，则显示左边的消息布局，将右边的消息布局隐藏
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.leftImg.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.rightImg.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
            holder.leftImg.setImageResource(R.drawable.head3);
        }else if (msg.getType() == Msg.TYPE_SENT){
            //如果发送的消息，则显示右边的消息布局，将左边的消息布局隐藏
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.rightImg.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.leftImg.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
            holder.rightImg.setImageResource(R.drawable.head5);
        }
    }

    @Override
    public int getItemCount(){
        return mMsgList.size();
    }
}
