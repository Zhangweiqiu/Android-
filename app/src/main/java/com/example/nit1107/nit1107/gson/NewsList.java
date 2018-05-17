package com.example.nit1107.nit1107.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by qiuzhangwi on 2018/5/17.
 */

public class NewsList {

    public int code;

    public String msg;

    @SerializedName("newslist")
    public List<Newss> newsList ;

}
