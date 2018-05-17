package com.example.nit1107.nit1107.util;

import com.example.nit1107.nit1107.gson.NewsList;
import com.google.gson.Gson;

/**
 * Created by qiuzhangwi on 2018/5/17.
 */

public class Utility {
    public static NewsList parseJsonWithGson(final String requestText){
        Gson gson = new Gson();
        return gson.fromJson(requestText, NewsList.class);
    }
}
