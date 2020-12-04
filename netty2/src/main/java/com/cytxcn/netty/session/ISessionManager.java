package com.cytxcn.netty.session;

import java.util.Collection;

public interface ISessionManager {
    ISession getSession(int sessionId);
    ISession getSessionByChannelId(Object channelId);

    ISession getSessionByUserId(int userId);

    void addSession(ISession session);
    void releaseSession(int sessionId);

    boolean login(int sessionId, int userId);

    boolean logout(int sessionId);

    int getUserId(int sessionId);

    boolean sendMsg(int sessionId, Object obj);
    int broadcastMsg(int[] sessionsIds, Object obj);

    void release();

    int size();

    Collection<ISession> getSessions();

}
