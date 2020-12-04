package com.cytxcn.netty.session;

import java.util.Collection;

import com.cytxcn.netty.protocol.ProtocolMsg;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public interface ISessionService {
	
    ISession newSession(Channel channel);

    ISession getSession(int sessionId);

    ISession getSessionByChannelId(Object channelId);

    ISession getSessionByUserId(int userId);

    void removeSession(int sessionId);

    void removeSessionByChannelId(Object channelId);

    boolean login(int sessionId, int userId);

    boolean logout(int sessionId);

    int getUserId(int sessionId);

    boolean sendMsg(int sessionId, Object obj);

    int size();

    Collection<ISession> getSessions();

    boolean killSession(int sessionId);

    int killSessions(Collection<? extends Number> sessionsIds);
    
    <T> void messageReceived(ChannelHandlerContext ctx, ProtocolMsg msg,int msgId);

}
