package com.example.nit1107.nit1107;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nit1107.nit1107.Adapter.ChatListAdapter;


public class ChatListFragment extends Fragment{
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle saveInstanceState)
    {
        View view = inflater.inflate(R.layout.chatlist_layout,container ,false);
        ChatListAdapter adapter = new ChatListAdapter(getContext(),R.layout.head_item,ChatlistActivity.friendList);
        ListView listView = view.findViewById(R.id.msg_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),ChatFragment.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
