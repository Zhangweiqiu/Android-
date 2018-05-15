package com.example.nit1107.nit1107.model;


public class Msg {

    public  static  final int TyPE_RECEIVED = 0;

    public static final int TYPE_SENT = 1;

    private String content;

    private int type;

    private String ToName;

    private String FromName;

    public String getToName() {
        return ToName;
    }

    public void setToName(String toName) {
        ToName = toName;
    }

    public String getFromName() {
        return FromName;
    }

    public void setFromName(String fromName) {
        FromName = fromName;
    }

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {

        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}