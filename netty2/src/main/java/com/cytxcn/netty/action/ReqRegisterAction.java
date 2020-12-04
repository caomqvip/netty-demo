package com.cytxcn.netty.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cytxcn.netty.action.base.AbstractBaseMessageAction;
import com.cytxcn.netty.protocol.ClientMsgInfo;
import com.cytxcn.netty.protocol.ClientMsgInfo.ReqRegister;
import com.cytxcn.netty.session.ISession;
import com.cytxcn.netty.session.ISessionService;

import io.netty.channel.ChannelHandlerContext;

@Component("eMsgToCSFromGS_ReqRegister")
public class ReqRegisterAction extends AbstractBaseMessageAction<ClientMsgInfo.ReqRegister>{
	
	@Autowired
	private  ISessionService sessionService;

	@Override
	public int getConnId(ChannelHandlerContext ctx, ReqRegister msg) {
		ISession session = sessionService.getSessionByChannelId(ctx.channel().id());
        if (session != null) {
            return session.getId();
        }
        return -1;
	}

	@Override
	public ReqRegister newObject() {
		return ClientMsgInfo.ReqRegister.newBuilder().build();
	}

	@Override
	public void process(int connId, ReqRegister req) throws Exception {
		ClientMsgInfo.RetRegister retRegister = ClientMsgInfo.RetRegister.newBuilder().
				setCode(666).setMsgid(ClientMsgInfo.MsgID.eMsgToCSFromGS_RetRegister).build();
		sessionService.sendMsg(connId, retRegister.toByteArray());
	}

	
	
	
	
	
	
	
	
	
	
	
	public ReqRegisterAction(ISessionService sessionService) {
		super();
		this.sessionService = sessionService;
	}

	public ReqRegisterAction() {
		super();
	}
}
