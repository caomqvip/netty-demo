package com.cytxcn.netty.server;






import com.cytxcn.netty.messages.ProtocolMsg;
import com.cytxcn.netty.session.ISessionService;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ProtoServerHandler extends  SimpleChannelInboundHandler<Object> {
	
	
	private ISessionService sessionService;
	

	
	public ProtoServerHandler(ISessionService sessionService) {
	
		this.sessionService = sessionService;
	}


	//每当从服务端读到客户端写入信息时
	@Override
	public void channelRead0(ChannelHandlerContext ctx, Object obj) throws Exception {

        Channel incoming = ctx.channel();
/*
    	ClientMsgInfo.ReqLogin msg = (ClientMsgInfo.ReqLogin)obj;
		System.out.println("Client->Server:"+incoming.remoteAddress()+"account: "+msg.getAccount()+
				"password: "+msg.getPassword()+"msgid: "+msg.getMsgid());*/
        
        
        
       // String if_sum=(String)JSONObject.fromObject(obj).get("msgid");
        if(obj instanceof ProtocolMsg) {
			ProtocolMsg msg = (ProtocolMsg)obj;
			System.out.println("Client->Server:"+incoming.remoteAddress()+msg.getBody());
			sessionService.messageReceived(ctx, msg, msg.getProtocolHeader().getMsgId());
		 }
        
        //ctx.writeAndFlush(obj);
	}
	
	
	//服务端监听到客户端活动
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + "在线");
		sessionService.newSession(ctx.channel());
	}
	
	//服务端监听到客户端不活动
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + "掉线");
		/*ISession session = sessionService.getSessionByChannelId(ctx.channel().id());
        if (session != null) {
        	sessionService.removeSessionByChannelId(ctx.channel().id());
        }*/
        ctx.close();
	}
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) // (7)
			throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + "异常");
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}

	

}
