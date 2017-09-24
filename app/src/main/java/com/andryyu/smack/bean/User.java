package com.andryyu.smack.bean;

import java.io.Serializable;

/**
 * Created by WH1705002 on 2017/6/7.
 */

public class User implements Serializable {
    private String mUsername;
    private String mNickname;
    private String mPassword;

    public User(String username, String password) {
        mUsername = username;
        mPassword = password;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setNickname(String nickname) {
        mNickname = nickname;
    }

    public String getNickname() {
        return mNickname;
    }
}
