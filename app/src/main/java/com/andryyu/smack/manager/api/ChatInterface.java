package com.andryyu.smack.manager.api;


import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smackx.disco.ServiceDiscoveryManager;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.List;

/**
 * Created by WH1705002 on 2017/6/6.
 */

public interface ChatInterface {

    /**
     * <p>getChatJid</p>
     * @param userName 用户账号
     * @return
     * @Description  获取聊天对象的jid值
     */
    String getChatJid(String userName);

    /**
     * <p>getFullJid</p>
     * @param userName
     * @return
     * @Description  文件传输jid
     */
    String getFullJid(String userName);

    /**
     * <p>getMultiChatJid</p>
     * @param roomName
     * @return
     * @Description  获取群聊JID
     */
    String getMultiChatJid(String roomName);

    /**
     * <p>createChat</p>
     * @param jid
     * @return
     * @Description  创建单人聊天
     */
    Chat createChat(String jid) throws XmppStringprepException;

    /**
     * <p>createChatRoom</p>
     * @param roomName
     * @param nickName
     * @param password
     * @return
     * @Description   创建群聊室
     */
    MultiUserChat createChatRoom(String roomName, String nickName, String password);

    /**
     * <p>joinChatRoom</p>
     * @param roomName
     * @param nickName
     * @param password
     * @return
     * @Description   加入群聊室
     */
    MultiUserChat  joinChatRoom(String roomName, String nickName, String password);

    /**
     * <p>getHostedRooms</p>
     * @return
     * @Description  获取服务器上的所有群聊房间
     */
    List<HostedRoom> getHostedRooms() throws Exception;

    /**
     * <p>getServiceDiscoveryManager</p>
     * @return
     * @Description
     */
    ServiceDiscoveryManager getServiceDiscoveryManager();

    /**
     * <p>getSendFileTransfer</p>
     * @param jid
     *          一个完整的jid(如：laohu@192.168.0.108/Smack，后面的Smack应该客户端类型，不加这个会出错)
     * @return
     * @Description  获取发送文件的发送器
     */
    OutgoingFileTransfer getSendFileTransfer(String jid);

    /**
     * <p>addFileTransferListener</p>
     * @param fileTransferListener
     * @Description   添加文件接收的监听
     */
    void addFileTransferListener(FileTransferListener fileTransferListener);
}
