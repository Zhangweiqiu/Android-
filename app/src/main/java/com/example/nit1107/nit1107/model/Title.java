package com.example.nit1107.nit1107.model;

/**
 * Created by qiuzhangwi on 2018/5/17.
 */

public class Title {

    private String title;
    private String descr;
    private String imageUrl;
    private String uri;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Title(String title, String descr, String imageUrl, String uri) {

        this.title = title;
        this.descr = descr;
        this.imageUrl = imageUrl;
        this.uri = uri;
    }
}
