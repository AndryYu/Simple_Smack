package com.andryyu.smack.manager.api;


import com.andryyu.smack.entity.LoginResult;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.disco.ServiceDiscoveryManager;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by WH1705002 on 2017/6/6.
 */

public class XmppInterface {

    //xmpp连接接口
    public interface connInterface {
        XMPPTCPConnection connect();
        boolean isConnected();
        XMPPTCPConnection getConnection();
        void disConnect();
    }

    // 聊天相关接口
    public interface ChatInterface {
        String getChatJid(String userName);
        String getFullJid(String userName);
        String getMultiChatJid(String roomName);
        Chat createChat(String jid) throws XmppStringprepException;
        MultiUserChat createChatRoom(String roomName, String nickName, String password);
        MultiUserChat  joinChatRoom(String roomName, String nickName, String password);
        List<HostedRoom> getHostedRooms() throws Exception;
        ServiceDiscoveryManager getServiceDiscoveryManager();
        OutgoingFileTransfer getSendFileTransfer(String jid);
        void addFileTransferListener(FileTransferListener fileTransferListener);
    }

    //用户相关接口
    public interface UserInfoInterface {
        LoginResult login(String accout, String pwd);
        String getAccountName();
        boolean updateUserState(int status);
        boolean registerUser(String user, String password, Map<String, String> attributes);
        boolean changePassword(String newpassword);
        Set getAccountAttributes();
        Set getAllFriends();
        RosterEntry getFriend(String user);
        boolean addFriend(String user, String nickName, String groupName);
        boolean deleteUser();
        boolean logout();
    }
}
