package com.cytxcn.netty.action;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cytxcn.netty.action.base.AbstractBaseMessageAction;
import com.cytxcn.netty.event.StudentEvent;
import com.cytxcn.netty.eventService.StudentService;
import com.cytxcn.netty.protocol.ClientMsgInfo;
import com.cytxcn.netty.protocol.ClientMsgInfo.ReqLogin;
import com.cytxcn.netty.session.ISession;
import com.cytxcn.netty.session.ISessionService;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author Administrator
 *
 */
@Component("eMsgToCSFromGS_ReqLogin")
public class ReqLoginAction extends AbstractBaseMessageAction<ClientMsgInfo.ReqLogin>{
	
	@Autowired
	private  ISessionService sessionService;
	
	@Autowired
	private ApplicationContext context;
	
	


	@Override
	public ReqLogin newObject() {
		return ClientMsgInfo.ReqLogin.newBuilder().build();
	}


	@Override
	public void process(int connId, ReqLogin req) throws Exception {
		
		ClientMsgInfo.RetLogin user = null;
		for (int i = 0; i < 8; i++) {
			user = ClientMsgInfo.RetLogin.newBuilder().
					setCode(i).setMsgid(ClientMsgInfo.MsgID.eMsgToCSFromGS_RetLogin).build();
			sessionService.sendMsg(connId, user.toByteArray());
			Thread.sleep(100);
		}
		try {
			StudentEvent studentEvent = new StudentEvent("cc",18, 1);
			context.publishEvent(studentEvent);
			
		} catch (Exception e) {
		
		}
		
		
	}

	@Override
	public int getConnId(ChannelHandlerContext ctx, ReqLogin msg) {
		ISession session = sessionService.getSessionByChannelId(ctx.channel().id());
        if (session != null) {
            return session.getId();
        }
        return -1;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ReqLoginAction(ISessionService sessionService) {
		super();
		this.sessionService = sessionService;
	}


	public ReqLoginAction() {
		super();
	}


	


	
	




	

	
	
}
