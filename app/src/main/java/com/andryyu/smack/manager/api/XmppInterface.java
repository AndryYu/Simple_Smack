package com.andryyu.smack.manager.api;


import com.andryyu.smack.bean.LoginResult;

import org.jivesoftware.smack.tcp.XMPPTCPConnection;

/**
 * Created by WH1705002 on 2017/6/6.
 */

public interface XmppInterface {

    /**
     * <p>connect</p>
     * @return
     * @Description  xmpp连接服务器
     */
    XMPPTCPConnection connect();

    /**
     * <p>isConnected</p>
     * @return
     * @Description  判断是否连接成功
     */
    boolean isConnected();

    /**
     * <p>getConnection</p>
     * @return
     * @Description  获取XMPPTCPConnection实例
     */
    XMPPTCPConnection getConnection();

    /**
     * <p>login</p>
     * @param accout
     * @param pwd
     * @Description  用户登录
     */
    LoginResult login(String accout, String pwd);

    /**
     * <p>getAccountName</p>
     * @return
     * @Description  获取账号昵称
     */
    String getAccountName();

    /**
     * <p>setUserStatus</p>
     * @param status
     * @Description  设置用户状态
     */
    boolean updateUserState(int status);

    /**
     * <p>disConnect</p>
     *
     * @Description  断开连接
     */
    void disConnect();
}
