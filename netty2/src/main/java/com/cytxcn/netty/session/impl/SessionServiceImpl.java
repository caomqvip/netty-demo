package com.cytxcn.netty.session.impl;

import java.util.Collection;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cytxcn.netty.action.ReqLoginAction;
import com.cytxcn.netty.action.ReqRegisterAction;
import com.cytxcn.netty.action.base.IMessageAction;
import com.cytxcn.netty.protocol.ClientMsgInfo;
import com.cytxcn.netty.protocol.ClientMsgInfo.MsgID;
import com.cytxcn.netty.protocol.ProtocolMsg;
import com.cytxcn.netty.session.ISession;
import com.cytxcn.netty.session.ISessionFactory;
import com.cytxcn.netty.session.ISessionManager;
import com.cytxcn.netty.session.ISessionService;
import com.google.protobuf.InvalidProtocolBufferException;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class SessionServiceImpl implements ISessionService {
	
	
	
	
	private final ISessionManager sessionManager = new NioSessionManager();
    private final ISessionFactory sessionFactory = new NioSessionFactory();
    
    
    @Autowired
	private  ISessionService sessionService;
    
    @Autowired
    private Map<String ,IMessageAction> messageActionMap;
    
    
	@Override
	public ISession newSession(Channel channel) {
		 ISession session = sessionFactory.newSession(channel);
	        if (session != null) {
	            sessionManager.addSession(session);
	        }
	        return session;
	}
	@Override
	public ISession getSession(int sessionId) {
		return sessionManager.getSession(sessionId);
	}
	@Override
	public ISession getSessionByChannelId(Object channelId) {
        return sessionManager.getSessionByChannelId(channelId);
	}
	@Override
	public ISession getSessionByUserId(int userId) {  
		return sessionManager.getSessionByUserId(userId);
	}
	@Override
	public void removeSession(int sessionId) {
		 ISession session = sessionManager.getSession(sessionId);
	        if (session != null) {
	            sessionManager.releaseSession(sessionId);
	            sessionFactory.releaseSession(session);
	        }
	}
	@Override
	public void removeSessionByChannelId(Object channelId) {
		 ISession session = sessionManager.getSessionByChannelId(channelId);
	        if (session != null) {
	            sessionManager.releaseSession(session.getId());
	            sessionFactory.releaseSession(session);
	        }
	}
	@Override
    public boolean login(int sessionId, int userId) {
        return sessionManager.login(sessionId, userId);
    }

    @Override
    public boolean logout(int sessionId) {
        return sessionManager.logout(sessionId);
    }

    @Override
    public int getUserId(int sessionId) {
        return sessionManager.getUserId(sessionId);
    }

    @Override
    public boolean sendMsg(int sessionId,Object obj) {
    	return internalSendMsg(sessionId, obj);
    	/*if (obj instanceof ClientMsgInfo) {
            try {
                Object msg = new Object();
            } catch (Exception e) {
            	e.printStackTrace();
                log.error("fail write message, sessionId={}, protoId={}", sessionId, e);
            }
        }
        return false;*/
    }


    @Override
    public int size() {
        return sessionManager.size();
    }

    @Override
    public Collection<ISession> getSessions() {
        return sessionManager.getSessions();
    }

    @Override
    public boolean killSession(int sessionId) {
        ISession session = getSession(sessionId);
        if (session != null) {
            log.info("kill session {}({})", session.getId(), session.getUserId());
            session.close();
            return true;
        } else {
            log.info("kill session {}, but not exist", sessionId);
            return false;
        }
    }

    @Override
    public int killSessions(Collection<? extends Number> sessionsIds) {
        return sessionsIds.stream().mapToInt(id -> killSession(id.intValue()) ? 1 : 0).sum();
    }
	
	
    
    private boolean internalSendMsg(int sessionId, Object msg) {
        ISession session = getSession(sessionId);
        if (session != null) {
            session.write(msg);
            return true;
        }
        return false;
    }
    
	@Override
	public <T> void messageReceived(ChannelHandlerContext ctx, ProtocolMsg msg,int msgId) {
		try {
			MsgID forNumber = ClientMsgInfo.MsgID.forNumber(msgId);
			IMessageAction iMessageAction2 = messageActionMap.get(forNumber.toString());
			iMessageAction2.processMessage(ctx,msg.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
