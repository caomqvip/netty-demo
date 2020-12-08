package com.cytxcn.netty.server;



import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cytxcn.netty.decoder.MyProtocolDecoder;
import com.cytxcn.netty.encoder.ProtobufEncoder;
import com.cytxcn.netty.messages.protobuf.ClientMsgInfo;
import com.cytxcn.netty.session.ISessionService;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

@Component
public class ProtoServerInitializer extends ChannelInitializer<SocketChannel> {
	
	
	@Autowired
	private ISessionService sessionService;
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		//心跳检测
		pipeline.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
		//client to server 解码器
        pipeline.addLast("decoder", new MyProtocolDecoder());	
        //server to client 编码器
        pipeline.addLast("encoder", new ProtobufEncoder());
        //心跳逻辑处理
        pipeline.addLast("HeartbeatHandler", new HeartbeatHandler());
        //逻辑处理类
        pipeline.addLast(new ProtoServerHandler(sessionService));
	}

	
	  

}
