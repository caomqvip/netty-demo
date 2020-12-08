package com.cytxcn.netty.server;

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;

import com.cytxcn.netty.messages.MessageCode;
import com.cytxcn.netty.messages.ProtocolMsg;
import com.cytxcn.netty.messages.protobuf.ClientMsgInfo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 
 * @author 心跳 处理 handler
 *
 */
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {
	private Logger logger = LoggerFactory.getLogger(HeartbeatHandler.class);
	private int heartbeatCount = 0;// 心跳计数器，如果一直接收到的是心跳消息，达到一定数量之后，说明客户端一直没有用户操作了，服务器就主动断开连接。
	private int maxHeartbeatCount = 66;// 最大心跳数

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {// 在这里接收channel中的事件信息
			IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
			if (idleStateEvent.state() == IdleState.READER_IDLE) {// 一定时间内，既没有收到客户端信息，则断开连接
				ctx.close();
				logger.info("连接读取空闲，断开连接，channelId:{}", ctx.channel().id().asShortText());
			}
		}
		ctx.fireUserEventTriggered(evt);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ProtocolMsg protocolMsg = (ProtocolMsg)msg;
		// 拦截心跳请求，并处理 
		if (protocolMsg.getProtocolHeader().getMsgId() == MessageCode.Heartbeat.getMessageId()) {
			//正常这里要返回信息  偷懒没有写
			
			/*HeartbeatMsgRequest request = new HeartbeatMsgRequest();
			request.readTransferMessage(gameMessagePackage);
			HeartbeatMsgResponse response = request.newCouple();
			ctx.writeAndFlush();*/
			
			
			System.out.println("第 "+heartbeatCount+" 次心跳请求接收");
			this.heartbeatCount++;
			if (this.heartbeatCount > maxHeartbeatCount) {
				ctx.close();
				logger.info("心跳时间达到最大值，主动断开连接：{}",ctx.channel().id().asShortText());
			}
		} else {
			this.heartbeatCount = 0;// 收到非心跳消息之后，重新计数
			ctx.fireChannelRead(msg);// 如果不是心跳消息，则向下发送消息，让后面的Handler去处理，如果不下发，后面的Handler将接收不到消息。
		}
	}
}
