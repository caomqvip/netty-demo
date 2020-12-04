package com.cytxcn.netty.server;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cytxcn.netty.decoder.MyProtocolDecoder;
import com.cytxcn.netty.encoder.ProtobufEncoder;
import com.cytxcn.netty.protocol.ClientMsgInfo;
import com.cytxcn.netty.session.ISessionService;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

@Component
public class ProtoServerInitializer extends ChannelInitializer<SocketChannel> {
	
	
	@Autowired
	private ISessionService sessionService;
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new MyProtocolDecoder());	
        pipeline.addLast("encoder", new ProtobufEncoder());				
        pipeline.addLast(new ProtoServerHandler(sessionService));
	}

	
	  

}
