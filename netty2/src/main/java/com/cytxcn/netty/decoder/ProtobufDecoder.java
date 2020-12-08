package com.cytxcn.netty.decoder;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cytxcn.netty.messages.protobuf.ClientMsgInfo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

@Component
public class ProtobufDecoder extends ByteToMessageDecoder {
	private static final int HEADER_LENGTH = 8;
	static ByteBuf readBytes ;

	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in == null) {
		    return;
		}
		if (in.readableBytes() < HEADER_LENGTH) {
            return;
        }
        in.markReaderIndex();
        readBytes = in.readBytes(8);
        
        int getInt = GetInt(0,new byte[readBytes.readableBytes()]);
        System.out.println("client-length:"+getInt);
        int getInt2 = GetInt(4,new byte[readBytes.readableBytes()]);
        System.out.println("client-msgId:"+getInt2);
        byte[] data = new byte[in.readableBytes()];//数据大小
        in.getBytes(HEADER_LENGTH, data);
        ClientMsgInfo.ReqLogin  user2 = ClientMsgInfo.ReqLogin.parseFrom(data);
		out.add(user2);
		
		
		
		in.skipBytes(in.readableBytes());
	}
	
	
	
	
	 private static int GetInt(int ix,byte[] readBuffer){
         return
             ((readBuffer[ix + 0] & 0xff) << 0x00) |
             ((readBuffer[ix + 1] & 0xff) << 0x08) |
             ((readBuffer[ix + 2] & 0xff) << 0x10) |
             ((readBuffer[ix + 3] & 0xff) << 0x18);
     }

}
