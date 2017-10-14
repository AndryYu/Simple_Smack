package com.andryyu.smack.manager;

import com.andryyu.smack.bean.LoginResult;
import com.andryyu.smack.bean.User;
import com.andryyu.smack.data.Constants;
import com.andryyu.smack.manager.api.XmppInterface;
import com.andryyu.smack.manager.listener.SmackConnectionListener;
import com.orhanobut.logger.Logger;


import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;

import java.io.IOException;


/**
 * Created by WH1705002 on 2017/6/6.
 */

public class XmppManager implements XmppInterface {

    private String TAG = XmppManager.class.getSimpleName();


    /** xmppConnection实例 */
    private static XMPPTCPConnection mConnection;
    private static volatile XmppManager xmppManager;


    private XmppManager() {
        this.mConnection = connect();
    }

    /**
     * <p>getInstance</p>
     *
     * @return
     * @Description  获取操作实例
     */
    public static XmppManager getInstance() {
        if (xmppManager == null) {
            synchronized (XmppManager.class) {
                if (xmppManager == null) {
                    xmppManager = new XmppManager();
                }
            }
        }
        return xmppManager;
    }


    @Override
    public XMPPTCPConnection connect() {
        try {
            SmackConfiguration.setDefaultPacketReplyTimeout(10000);
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    //是否开启安全模式
                    .setSecurityMode(XMPPTCPConnectionConfiguration.SecurityMode.disabled)
                    //服务器名称
                    .setServiceName(Constants.SERVER_NAME)
                    .setHost(Constants.SERVER_IP)//服务器IP地址
                    //服务器端口
                    .setPort(Constants.PORT)
                    //是否开启压缩
                    .setCompressionEnabled(false)
                    //开启调试模式
                    .setDebuggerEnabled(true).build();

            XMPPTCPConnection connection = new XMPPTCPConnection(config);
            ReconnectionManager reconnectionManager = ReconnectionManager.getInstanceFor(connection);
            reconnectionManager.enableAutomaticReconnection();//允许自动重连
            reconnectionManager.setFixedDelay(2);//重连间隔时间
            connection.addConnectionListener(new SmackConnectionListener());//连接监听
            connection.connect();
            return connection;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public XMPPTCPConnection getConnection() {
        if (!isConnected() || mConnection == null) {
            throw new IllegalStateException("服务器断开，请先连接服务器");
        }
        return mConnection;
    }

    @Override
    public boolean isConnected() {
        if (mConnection == null)
            xmppManager = new XmppManager();

        if(mConnection == null)
            return false;

        if (!mConnection.isConnected()) {
            try {
                mConnection.connect();
                return true;
            } catch (SmackException | IOException | XMPPException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public LoginResult login(String username, String password) {
        try {
            if (!isConnected()) {
                throw new IllegalStateException("服务器断开，请先连接服务器");
            }
            mConnection.login(username, password);
            User user = new User(username, password);
            user.setNickname(getAccountName());
            return new LoginResult(user, true);
        } catch (Exception e) {
            Logger.e(TAG, e, "login failure");
            return new LoginResult(false, e.getMessage());
        }
    }


    public String getAccountName() {
        if (isConnected()) {
            try {
                return AccountManager.getInstance(mConnection).getAccountAttribute("name");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }

    @Override
    public boolean updateUserState(int status) {
        if(mConnection==null){
            return false;
        }
        Presence presence = new Presence(Presence.Type.available);
        switch (status){
            case 0: //设置在线
                presence.setMode(Presence.Mode.available);
                break;
            case 1: //设置Q我吧
                presence.setMode(Presence.Mode.chat);
                break;
            case 2: //设置忙碌
                presence.setMode(Presence.Mode.dnd);
                break;
            case 3: //离开
                presence.setMode(Presence.Mode.away);
                break;
            case 4: //小憩一会
                presence.setMode(Presence.Mode.xa);
                break;
            case 5: //设置离线
                presence = new Presence(Presence.Type.unavailable);
                break;
        }
        try {
            // Send the stanza (assume we have an XMPPConnection instance called "xmppConnection").
            mConnection.sendStanza(presence);
            return true;
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void disConnect() {
        if(mConnection!=null){
            mConnection.disconnect();
            mConnection=null;
        }
    }
}
