package com.example.nit1107.nit1107;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nit1107.nit1107.model.News;

import java.util.ArrayList;

/**
 * Created by qiuzhangwi on 2018/5/16.
 */

public class NewsListFragment extends Fragment{


    private View view;

    private SwipeRefreshLayout swipeRefreshLayout;

    private ListView  listView;

    Context mContext;

    private ArrayList<News> newsArrayList = new ArrayList<>();


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
        listView  = view.findViewById(R.id.Lv);
        listView.setAdapter(new NewsAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("--------------",position+"-----------------------");
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    public void init(){
        for (int i = 0;i<5;i++){
            News news = new News();
            news.setTitle("鸟瞰暴雨后的武汉 全市已转移16万人次");
            news.setDes("7月5-6日，武汉普降暴雨-大暴雨，中心城区、蔡甸部分地区出现特大暴雨。江夏大道汤逊湖大桥段，被湖水冲破的路障。记者贾代腾飞 陈卓摄");
            news.setNewsImgUrl("http://slide.news.sina.com.cn/s/slide_1_2841_101020.html#p=1");
            newsArrayList.add(news);
        }
    }


    public class  NewsAdapter extends BaseAdapter{
        @Override
        public int  getCount() {
            return newsArrayList.size();
        }

        @Override
        public News getItem(int position) {
            return newsArrayList.get(position);
        }

        @Override
        public long  getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(getContext(), R.layout.item, null);
                holder.tv_title =  convertView.findViewById(R.id.tv_title);
                holder.tv_des =  convertView.findViewById(R.id.tv_des);
                holder.iv_icon =  convertView.findViewById(R.id.iv_icon);
                convertView.setTag(holder);
            } else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            News item = getItem(position);
            holder.tv_title.setText(item.getTitle());
            holder.tv_des.setText(item.getDes());
            holder.iv_icon.setImageDrawable(Drawable.createFromPath(item.getNewsImgUrl()));
            return convertView;
        }
    }

    private static  class  ViewHolder {
        TextView tv_title;
        TextView tv_des;
        ImageView iv_icon;
    }

}
