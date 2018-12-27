package com.yaoh.AndroidDemo.dm.bean;

/**
 * Package com.yaoh.demo.dm.bean.
 * Created by yaoh on 2017/09/07.
 * <p/>
 * Description:
 */
public class User {
    private String user_name;
    private String user_password;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                '}';
    }
}
