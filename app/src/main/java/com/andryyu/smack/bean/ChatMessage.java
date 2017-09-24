package com.andryyu.smack.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.rnxmppclient.data.FileLoadState;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by WH1705002 on 2017/6/8.
 */
@Entity
public class ChatMessage implements Parcelable {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String uuid;
    //消息内容
    private String message;
    //消息类型
    private int messageType;
    //聊天好友的用户名
    private String friendUserName;
    //聊天好友的昵称
    private String friendNickName;
    //自己的用户名
    private String meUserName;
    //自己的昵称
    private String meNickName;
    //消息发送接收时间
    private String dateTime;
    // 消息是否是自己发出的
    private boolean isMeSend;
    //接收的图片和语音路径
    private String filePath;
    //文件加载状态
    private int fileLoadState = FileLoadState.STATE_LOAD_START.value();
    // 是否为群聊记录
    private boolean isMulti = false;
   /* @Generated(hash = 533675659)
    public ChatMessage(Long id, String uuid, String message, int messageType,
            String friendUserName, String friendNickName, String meUserName,
            String meNickName, String dateTime, boolean isMeSend, String filePath,
            int fileLoadState, boolean isMulti) {
        this.id = id;
        this.message = message;
        this.messageType = messageType;
        this.friendUserName = friendUserName;
        this.friendNickName = friendNickName;
        this.meUserName = meUserName;
        this.meNickName = meNickName;
        this.isMeSend = isMeSend;
        this.filePath = filePath;
        this.fileLoadState = fileLoadState;
        this.isMulti = isMulti;
        this.uuid = uuid;
        this.dateTime = dateTime;
    }
    @Generated(hash = 2271208)
    public ChatMessage() {
    }*/

    protected ChatMessage(Parcel in) {
        uuid = in.readString();
        message = in.readString();
        messageType = in.readInt();
        friendUserName = in.readString();
        friendNickName = in.readString();
        meUserName = in.readString();
        meNickName = in.readString();
        dateTime = in.readString();
        isMeSend = in.readByte() != 0;
        filePath = in.readString();
        fileLoadState = in.readInt();
        isMulti = in.readByte() != 0;
    }

    @Generated(hash = 533675659)
    public ChatMessage(Long id, String uuid, String message, int messageType,
            String friendUserName, String friendNickName, String meUserName,
            String meNickName, String dateTime, boolean isMeSend, String filePath,
            int fileLoadState, boolean isMulti) {
        this.id = id;
        this.uuid = uuid;
        this.message = message;
        this.messageType = messageType;
        this.friendUserName = friendUserName;
        this.friendNickName = friendNickName;
        this.meUserName = meUserName;
        this.meNickName = meNickName;
        this.dateTime = dateTime;
        this.isMeSend = isMeSend;
        this.filePath = filePath;
        this.fileLoadState = fileLoadState;
        this.isMulti = isMulti;
    }

    @Generated(hash = 2271208)
    public ChatMessage() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeString(message);
        dest.writeInt(messageType);
        dest.writeString(friendUserName);
        dest.writeString(friendNickName);
        dest.writeString(meUserName);
        dest.writeString(meNickName);
        dest.writeString(dateTime);
        dest.writeByte((byte) (isMeSend ? 1 : 0));
        dest.writeString(filePath);
        dest.writeInt(fileLoadState);
        dest.writeByte((byte) (isMulti ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChatMessage> CREATOR = new Creator<ChatMessage>() {
        @Override
        public ChatMessage createFromParcel(Parcel in) {
            return new ChatMessage(in);
        }

        @Override
        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public int getMessageType() {
        return this.messageType;
    }
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
    public String getFriendUserName() {
        return this.friendUserName;
    }
    public void setFriendUserName(String friendUserName) {
        this.friendUserName = friendUserName;
    }
    public String getFriendNickName() {
        return this.friendNickName;
    }
    public void setFriendNickName(String friendNickName) {
        this.friendNickName = friendNickName;
    }
    public String getMeUserName() {
        return this.meUserName;
    }
    public void setMeUserName(String meUserName) {
        this.meUserName = meUserName;
    }
    public String getMeNickName() {
        return this.meNickName;
    }
    public void setMeNickName(String meNickName) {
        this.meNickName = meNickName;
    }
    public String getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    public boolean getIsMeSend() {
        return this.isMeSend;
    }
    public void setIsMeSend(boolean isMeSend) {
        this.isMeSend = isMeSend;
    }
    public String getFilePath() {
        return this.filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public int getFileLoadState() {
        return this.fileLoadState;
    }
    public void setFileLoadState(int fileLoadState) {
        this.fileLoadState = fileLoadState;
    }
    public boolean getIsMulti() {
        return this.isMulti;
    }
    public void setIsMulti(boolean isMulti) {
        this.isMulti = isMulti;
    }
}
