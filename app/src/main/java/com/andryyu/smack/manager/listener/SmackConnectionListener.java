package com.andryyu.smack.manager.listener;

import com.orhanobut.logger.Logger;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

/**
 * Created by WH1705002 on 2017/6/7.
 */
public class SmackConnectionListener implements ConnectionListener {

    @Override
    public void connected(XMPPConnection connection) {
        Logger.d("connection connected");
    }

    @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {
        Logger.d("connection authenticated");
    }

    @Override
    public void connectionClosed() {
        Logger.d("connection connectionClosed");
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        Logger.d("connectionClosedOnError");
    }

    @Override
    public void reconnectingIn(int seconds) {
        Logger.d("connection reconnectingIn " + seconds + " second");
    }

    @Override
    public void reconnectionFailed(Exception e) {
        Logger.d("reconnectionFailed");
    }

    @Override
    public void reconnectionSuccessful() {
        Logger.d("reconnectionSuccessful");
    }
}
