package com.example.nit1107.nit1107;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nit1107.nit1107.Adapter.TitleAdapter;
import com.example.nit1107.nit1107.gson.NewsList;
import com.example.nit1107.nit1107.gson.Newss;
import com.example.nit1107.nit1107.model.News;
import com.example.nit1107.nit1107.model.Title;
import com.example.nit1107.nit1107.util.HttpUtil;
import com.example.nit1107.nit1107.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by qiuzhangwi on 2018/5/16.
 */

public class NewsListFragment extends Fragment{

    private int it ;

    private View view;

    private SwipeRefreshLayout swipeRefreshLayout;

    private ListView  listView;

    private TitleAdapter adapter;

    Context mContext;

    private ArrayList<News> newsArrayList = new ArrayList<>();


    private List<Title> titleList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_listcontent_layout,container,false);
        initView();
        return view;
    }

    private void  initView(){
        swipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        listView  = view.findViewById(R.id.Lv);
        adapter = new TitleAdapter(getContext(),R.layout.item,titleList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent = new Intent(getActivity(),ContentActivity.class);
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Title title = titleList.get(position);
                intent.putExtra("title", title.getTitle());
                intent.putExtra("url",title.getUri());
                startActivity(intent);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                requestNew(it);
            }
        });
        requestNew(0);

    }

    public void init(int it){
        this.it = it;
    }


    /**
     *  判断是否是当前页面,如果不是则 请求处理数据
     */
    private void handleCurrentPage(String text,int item){
        if (!"".equals(text)){
            requestNew(item);
        }
    }


    /**
     * 请求处理数据
     */
    public void requestNew(int itemName){
        // 根据返回到的 URL 链接进行申请和返回数据
        final String address = response(itemName);    // key
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "新闻加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final NewsList  newsList = Utility.parseJsonWithGson(responseText);
                final int code = newsList.code;
                final String msg = newsList.msg;
                if (code == 200) {
                    titleList.clear();
                    for (Newss newss : newsList.newsList) {
                        Title title = new Title(newss.title, newss.description, newss.picUrl, newss.url);
                        titleList.add(title);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                listView.setSelection(0);
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "数据错误返回",Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        });
    }


    /**
     * 输入不同的类型选项，返回对应的 URL 链接
     */
    private String response(int itemName){
        String address = "https://api.tianapi.com/social/?key=94d7a64d8f2b99aa7ec5fb862b62584d&num=50&rand=1";
        switch(itemName){
            case Other.ITEM_SOCIETY:
                break;
            case Other.ITEM_COUNTY:
                address = address.replaceAll("social","guonei");
//                address="https://api.tianapi.com/guonei/?key=339a8b166f397f008236e596616a5f54&num=50&rand=1";
                break;
            case Other.ITEM_INTERNATION:
                address = address.replaceAll("social","world");
//                address="https://api.tianapi.com/world/?key=339a8b166f397f008236e596616a5f54&num=50&rand=1";
                break;
            default:
        }
        return address;
    }

}
