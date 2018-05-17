package com.example.nit1107.nit1107.model;

import android.graphics.drawable.Drawable;

/**
 * Created by qiuzhangwi on 2018/5/14.
 */

public class News {

    //新闻标题
    private String title;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    //新闻发布时间
    private  String des;

    //新闻图片URl地址
    private String newsImgUrl;

    //新闻详情Url地址
    private String newsUrl;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getNewsImgUrl() {
        return newsImgUrl;
    }

    public void setNewsImgUrl(String newsImgUrl) {
        this.newsImgUrl = newsImgUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}
