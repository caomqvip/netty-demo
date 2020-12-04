/**
 * 
 */
package com.cytxcn.netty.server;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 说明：自定义协议服务端
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月5日 
 */
@Component
public class ProtocolServer {
	
	@Autowired
	private ProtoServerInitializer initializer;

	private  int port =8082;
	

	public ProtocolServer() {
		super();
	}
	/**
	 * 
	 */
	public ProtocolServer(int port) {
		this.port = port;
	}

	 @PostConstruct
	 public void run() throws Exception {
	        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
	        EventLoopGroup workerGroup = new NioEventLoopGroup();
	        try {
	            ServerBootstrap b = new ServerBootstrap(); // (2)
	            b.group(bossGroup, workerGroup)
	             .channel(NioServerSocketChannel.class) // (3)
	             .childHandler(initializer)
	             .option(ChannelOption.SO_BACKLOG, 128)          // (5)
	             .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

	            // 绑定端口，开始接收进来的连接
	            ChannelFuture f = b.bind(port).sync(); // (7)
	            
	    		System.out.println("Server start listen at " + port );
	    		
	            // 等待服务器  socket 关闭 。
	            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
	            f.channel().closeFuture().sync();
	            

	        } finally {
	            workerGroup.shutdownGracefully();
	            bossGroup.shutdownGracefully();
	        }
	    }
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8082;
        }
        new ProtocolServer(port).run();
	}

}
