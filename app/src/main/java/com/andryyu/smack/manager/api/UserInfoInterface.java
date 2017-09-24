package com.andryyu.smack.manager.api;

import org.jivesoftware.smack.roster.RosterEntry;

import java.util.Map;
import java.util.Set;

/**
 * Created by WH1705002 on 2017/6/6.
 */

public interface UserInfoInterface {
    /**
     * <p>registerUser</p>
     * @param user
     * @param password
     * @param attributes
     * @return
     * @Description   注册用户
     */
    boolean registerUser(String user, String password, Map<String, String> attributes);

    /**
     * <p>changePassword</p>
     * @param newpassword
     * @return
     * @Description  修改用户密码
     */
    boolean changePassword(String newpassword);

    /**
     * <p>getAccountAttributes</p>
     * @return
     * @Description  获取用户属性
     */
    Set getAccountAttributes();

    /**
     * <p>getAllFriends</p>
     * @return
     * @Description  获取所有的朋友
     */
    Set getAllFriends();

    /**
     * <p>getFriend</p>
     * @param user
     * @return
     * @Description  获取好友的信息
     */
    RosterEntry getFriend(String user);

    /**
     * <p>addFriend</p>
     * @param user
     * @param nickName
     * @param groupName
     * @return
     */
    boolean addFriend(String user, String nickName, String groupName);

    /**
     * <p>deleteUser</p>
     * @return
     * @Description  注销用户
     */
    boolean deleteUser();

    /**
     * <p>logout</p>
     * @return
     * @Description  退出
     */
    boolean logout();
}
