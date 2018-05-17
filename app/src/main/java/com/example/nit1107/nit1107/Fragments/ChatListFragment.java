package com.example.nit1107.nit1107.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nit1107.nit1107.Activity.ChatActivity;
import com.example.nit1107.nit1107.Activity.ChatlistActivity;
import com.example.nit1107.nit1107.Adapter.ChatListAdapter;
import com.example.nit1107.nit1107.R;
import com.example.nit1107.nit1107.model.Friend;


public class ChatListFragment extends Fragment{
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle saveInstanceState)
    {
        View view = inflater.inflate(R.layout.chatlist_layout,container ,false);
        ChatListAdapter adapter = new ChatListAdapter(getContext(),R.layout.head_item, ChatlistActivity.friendList);
        ListView listView = view.findViewById(R.id.msg_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend friend = ChatlistActivity.friendList.get(position);

                Intent intent = new Intent(getContext(),ChatActivity.class);

                intent.putExtra("friendName",friend.getName());
                startActivity(intent);
            }
        });

        return view;
    }

}
