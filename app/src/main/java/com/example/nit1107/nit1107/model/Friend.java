package com.example.nit1107.nit1107.model;

/**
 * Created by qiuzhangwi on 2018/5/13.
 */

public class Friend {
    private String name;

    private Integer imgId;

    public Friend(String name, Integer imgId) {
        this.name = name;
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }
}
