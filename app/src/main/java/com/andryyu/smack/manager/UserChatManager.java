package com.andryyu.smack.manager;

import com.rnxmppclient.data.Constants;
import com.rnxmppclient.manager.api.ChatInterface;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.disco.ServiceDiscoveryManager;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.FormField;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WH1705002 on 2017/6/6.
 */

public class UserChatManager implements ChatInterface{

    private static XMPPTCPConnection connection;
    private static ChatManager chatManager;
    private static MultiUserChatManager mucManager;
    private static XmppManager xmppManager;
    private Map<String, Chat> chatMap = new HashMap<String, Chat>();// 聊天窗口管理map集合

    /** 线程安全单例模式 */
    private static class SingletonHolder {
        private static final UserChatManager INSTANCE = new UserChatManager();
    }

    /**
     * <p>getChatManager</p>
     * @return
     * @Description 获取聊天对象管理器
     */
    public static  UserChatManager getChatManager() {
        xmppManager = XmppManager.getInstance();
        connection = xmppManager.getConnection();

        if(xmppManager.isConnected()) {
            chatManager = ChatManager.getInstanceFor(connection);
            MultiUserChatManager mucManager = MultiUserChatManager.getInstanceFor(connection);
            return SingletonHolder.INSTANCE;
        }

        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }

    @Override
    public String getChatJid(String userName) {
        if (!xmppManager.isConnected())
            throw new NullPointerException("服务器连接失败，请先连接服务器");

        return userName + "@" + connection.getServiceName();
    }

    @Override
    public String getFullJid(String userName) {
        if (!xmppManager.isConnected())
            throw new NullPointerException("服务器连接失败，请先连接服务器");

        return userName + "@" + connection.getServiceName() + "/" + Constants.XMPP_CLIENT;
    }

    @Override
    public String getMultiChatJid(String roomName) {
        return roomName + Constants.MULTI_CHAT_ADDRESS_SPLIT + connection.getServiceName();
    }

    @Override
    public Chat createChat(String user) throws XmppStringprepException {
        if(!xmppManager.isConnected())
            throw new NullPointerException("服务器连接失败，请先连接服务器");

        /** 判断是否创建聊天窗口 */
        for (String fristr : chatMap.keySet()) {
            if (fristr.equals(user)) {
                // 存在聊天窗口，则返回对应聊天窗口
                return chatMap.get(fristr);
            }
        }
        /** 创建聊天窗口 */
        Chat chat = chatManager.createChat(user);
        /** 添加聊天窗口到chatManage */
        chatMap.put(user, chat);
        return chat;
    }

    @Override
    public MultiUserChat createChatRoom(String roomName, String nickName, String password) {
        if(xmppManager.isConnected())
            throw new NullPointerException("服务器连接失败，请先连接服务器");

        MultiUserChat muc = null;
        try {
            // 创建一个MultiUserChat
            muc = mucManager.getMultiUserChat(roomName);
            // 创建聊天室
            boolean isCreated = true;//
            muc.createOrJoin(nickName);
            if(isCreated) {
                // 获得聊天室的配置表单
                Form form = muc.getConfigurationForm();
                // 根据原始表单创建一个要提交的新表单。
                Form submitForm = form.createAnswerForm();
                // 向要提交的表单添加默认答复
                List<FormField> fields = form.getFields();
                for(int i = 0; fields != null && i < fields.size(); i++) {
                    if(FormField.Type.hidden != fields.get(i).getType() && fields.get(i).getVariable() != null) {
                        // 设置默认值作为答复
                        submitForm.setDefaultAnswer(fields.get(i).getVariable());
                    }
                }
                // 设置聊天室的新拥有者
                List owners = new ArrayList();
                owners.add(connection.getUser());// 用户JID
                submitForm.setAnswer("muc#roomconfig_roomowners", owners);
                // 设置聊天室是持久聊天室，即将要被保存下来
                submitForm.setAnswer("muc#roomconfig_persistentroom", true);
                // 房间仅对成员开放
                submitForm.setAnswer("muc#roomconfig_membersonly", false);
                // 允许占有者邀请其他人
                submitForm.setAnswer("muc#roomconfig_allowinvites", true);
                if(password != null && password.length() != 0) {
                    // 进入是否需要密码
                    submitForm.setAnswer("muc#roomconfig_passwordprotectedroom",  true);
                    // 设置进入密码
                    submitForm.setAnswer("muc#roomconfig_roomsecret", password);
                }
                // 能够发现占有者真实 JID 的角色
                // submitForm.setAnswer("muc#roomconfig_whois", "anyone");
                // 登录房间对话
                submitForm.setAnswer("muc#roomconfig_enablelogging", true);
                // 仅允许注册的昵称登录
                submitForm.setAnswer("x-muc#roomconfig_reservednick", true);
                // 允许使用者修改昵称
                submitForm.setAnswer("x-muc#roomconfig_canchangenick", false);
                // 允许用户注册房间
                submitForm.setAnswer("x-muc#roomconfig_registration", false);
                // 发送已完成的表单（有默认值）到服务器来配置聊天室
                muc.sendConfigurationForm(submitForm);
            }
        } catch (Exception  e) {
            e.printStackTrace();
            return null;
        }
        return muc;
    }

    @Override
    public MultiUserChat joinChatRoom(String roomName, String nickName, String password) {
        if(!xmppManager.isConnected()) {
            throw new NullPointerException("服务器连接失败，请先连接服务器");
        }
        try {
            // 使用XMPPConnection创建一个MultiUserChat窗口
            MultiUserChat muc = mucManager.getMultiUserChat(roomName);
            // 聊天室服务将会决定要接受的历史记录数量
            DiscussionHistory history = new DiscussionHistory();
            history.setMaxChars(0);
            // history.setSince(new Date());
            // 用户加入聊天室
            muc.join(nickName, password);
            return muc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<HostedRoom> getHostedRooms() throws Exception {
        if (!xmppManager.isConnected()) {
            throw new NullPointerException("服务器连接失败，请先连接服务器");
        }
        return mucManager.getHostedRooms(connection.getServiceName());
    }

    @Override
    public ServiceDiscoveryManager getServiceDiscoveryManager() {
        if (!xmppManager.isConnected()) {
            throw new NullPointerException("服务器连接失败，请先连接服务器");
        }
        return ServiceDiscoveryManager.getInstanceFor(connection);
    }

    @Override
    public OutgoingFileTransfer getSendFileTransfer(String jid) {
        if (xmppManager.isConnected())
            return FileTransferManager.getInstanceFor(connection).createOutgoingFileTransfer(jid);

        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }

    @Override
    public void addFileTransferListener(FileTransferListener fileTransferListener) {
        if (xmppManager.isConnected()) {
            FileTransferManager.getInstanceFor(connection).addFileTransferListener(fileTransferListener);
            return;
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }
}
