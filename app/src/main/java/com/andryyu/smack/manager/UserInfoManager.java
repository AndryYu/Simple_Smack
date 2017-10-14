package com.andryyu.smack.manager;

import com.andryyu.smack.entity.LoginResult;
import com.andryyu.smack.entity.User;
import com.andryyu.smack.manager.api.XmppInterface;
import com.orhanobut.logger.Logger;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.iqregister.AccountManager;

import java.util.Map;
import java.util.Set;

/**
 * Created by WH1705002 on 2017/6/6.
 */

public class UserInfoManager implements XmppInterface.UserInfoInterface {

    private String  TAG = UserInfoManager.class.getSimpleName();
    private static XMPPTCPConnection xmppConnection;
    private static XmppManager xmppManager;
    private static AccountManager accountManager;
    private static UserInfoManager infoManager;


    public UserInfoManager(XmppManager xmppManager){
        this.xmppManager = xmppManager;
        xmppConnection = xmppManager.getConnection();
        accountManager = AccountManager.getInstance(xmppConnection);
    }

    @Override
    public LoginResult login(String username, String password) {
        try {
            if (!xmppManager.isConnected()) {
                throw new IllegalStateException("服务器断开，请先连接服务器");
            }
            xmppConnection.login(username, password);
            User user = new User(username, password);
            user.setNickname(getAccountName());
            return new LoginResult(user, true);
        } catch (Exception e) {
            Logger.e(TAG, e, "login failure");
            return new LoginResult(false, e.getMessage());
        }
    }

    @Override
    public String getAccountName() {
        if (xmppManager.isConnected()) {
            try {
                return AccountManager.getInstance(xmppConnection).getAccountAttribute("name");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }

    @Override
    public boolean updateUserState(int status) {
        if(xmppConnection==null){
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
            xmppConnection.sendStanza(presence);
            return true;
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean registerUser(String username, String password, Map<String, String> attributes) {
        if (!xmppManager.isConnected())
            return false;

        try {
            accountManager.createAccount(username, password, attributes);
            return true;
        } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException
                | SmackException.NotConnectedException e) {
            Logger.e(TAG, "register failure", e);
            return false;
        }
    }

    @Override
    public boolean changePassword(String newpassword) {
        if (!xmppManager.isConnected())
            return false;

        try {
            accountManager.changePassword(newpassword);
            return true;
        } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
            Logger.e(TAG, "change password failure", e);
            return false;
        }
    }

    @Override
    public Set getAccountAttributes() {
        if(xmppManager.isConnected()){
            try {
                return accountManager.getAccountAttributes();
            } catch (Exception e) {
                throw  new RuntimeException(e);
            }
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }

    @Override
    public Set getAllFriends() {
        if(xmppManager.isConnected()){
            return Roster.getInstanceFor(xmppConnection).getEntries();
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }

    @Override
    public RosterEntry getFriend(String user) {
        if(xmppManager.isConnected()){
            return Roster.getInstanceFor(xmppConnection).getEntry(user);
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }

    @Override
    public boolean addFriend(String user, String nickName, String groupName) {
        if(xmppManager.isConnected()){
            try {
                 Roster.getInstanceFor(xmppConnection).createEntry(user, nickName, new String[]{groupName});
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }

    @Override
    public boolean deleteUser() {
        try {
            accountManager.deleteAccount();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        }
    }

    @Override
    public boolean logout() {
        if (!xmppManager.isConnected()) {
            return false;
        }
        try {
            xmppConnection.instantShutdown();
            return true;
        } catch (Exception e) {
            Logger.e(TAG, e, "logout failure");
            return false;
        }
    }
}
