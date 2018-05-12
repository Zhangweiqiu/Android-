package com.example.nit1107.nit1107.db;


import org.litepal.crud.DataSupport;

public class UserAccount extends DataSupport{

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    private String name;
    private String account;
    private String password;
}
