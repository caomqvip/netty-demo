package com.cytxcn.netty.session;

import java.util.Date;

public interface ISession {
    int getId();

    int getUserId();

    Date getLoginTime();

    Date getCreateTime();

    Object getChannelId();

    long getRecvBytes();

    long getSendBytes();

    String getAddressInfo();

    long getLastRecvTimestamp();

    void login(int userId);

    int logout();

    void onRecv(Object obj);

    void write(Object obj);

    void close();
}
