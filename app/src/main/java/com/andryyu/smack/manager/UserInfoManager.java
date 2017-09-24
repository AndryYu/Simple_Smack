package com.andryyu.smack.manager;

import com.orhanobut.logger.Logger;
import com.rnxmppclient.manager.api.UserInfoInterface;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.iqregister.AccountManager;

import java.util.Map;
import java.util.Set;

/**
 * Created by WH1705002 on 2017/6/6.
 */

public class UserInfoManager implements UserInfoInterface {

    private String  TAG = UserInfoManager.class.getSimpleName();
    private static XMPPTCPConnection xmppConnection;
    private static XmppManager xmppManager;
    private static AccountManager accountManager;
    private static UserInfoManager infoManager;

    public static UserInfoManager getInstance(){
        xmppManager = XmppManager.getInstance();
        xmppConnection = xmppManager.getConnection();
        infoManager = new UserInfoManager();
        accountManager = AccountManager.getInstance(xmppConnection);
        return  infoManager;
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
