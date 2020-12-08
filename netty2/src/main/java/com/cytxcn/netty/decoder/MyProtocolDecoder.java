package com.cytxcn.netty.decoder;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.cytxcn.netty.messages.ProtocolHeader;
import com.cytxcn.netty.messages.ProtocolMsg;
import com.cytxcn.netty.messages.protobuf.ClientMsgInfo;
import com.cytxcn.netty.messages.protobuf.ClientMsgInfo.MsgID;
import com.google.protobuf.InvalidProtocolBufferException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

@Component
public class MyProtocolDecoder extends ByteToMessageDecoder {
	private static final int HEADER_LENGTH = 8;
	
	private static final int HEADER_SIZE = 10;

	
	private static int GetInt(int ix,byte[] readBuffer){
        return
            ((readBuffer[ix + 0] & 0xff) << 0x00) |
            ((readBuffer[ix + 1] & 0xff) << 0x08) |
            ((readBuffer[ix + 2] & 0xff) << 0x10) |
            ((readBuffer[ix + 3] & 0xff) << 0x18);
    }
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in == null) {
		    return;
		}
		
		if (in.readableBytes() < HEADER_SIZE) {
			return;// response header is 10 bytes
		}
		
		in.markReaderIndex();
		
		/*byte readByte1 = in.readByte();
		byte readByte2 = in.readByte();
		byte readByte3 = in.readByte();
		byte readByte4 = in.readByte();
		byte[] length = {readByte1,readByte2,readByte3,readByte4};
		
		int lengthInt = GetInt(0,length);
		
		int lengthInt = bytesToInt(length);*/
		
		byte readByte1 = in.readByte();
		byte readByte2 = in.readByte();
		byte readByte3 = in.readByte();
		byte readByte4 = in.readByte();
		byte[] msgId = {readByte1,readByte2,readByte3,readByte4};
		
//		int msgIdInt = GetInt(0,msgId);
		
		int msgIdInt = bytesToInt(msgId);
		
		MsgID msgid = ClientMsgInfo.MsgID.forNumber(msgIdInt);
		
		byte[] data = new byte[in.readableBytes()];//数据大小
		in.getBytes(4, data);
		
		
		
		ProtocolMsg msg = new ProtocolMsg();
		ProtocolHeader protocolHeader = new ProtocolHeader(msgIdInt,12);
		msg.setProtocolHeader(protocolHeader);
		msg.setBody(getObj(msgIdInt,data));
		out.add(msg);
		
		
		
		
		/*ByteBuf readBytes = in.readBytes(8);
        int getInt = GetInt(0,new byte[readBytes.readableBytes()]);
        int getInt2 = GetInt(4,new byte[readBytes.readableBytes()]);
        byte[] data = new byte[in.readableBytes()];//数据大小
        in.getBytes(HEADER_LENGTH, data);
        ClientMsgInfo.ReqLogin  user2 = ClientMsgInfo.ReqLogin.parseFrom(data);
        out.add(data);*/

		
		
		
	    
	    in.skipBytes(in.readableBytes());
	}
	private static Object getObj(int msgId, byte[] data) throws InvalidProtocolBufferException{
		switch (msgId) {
			case 8293:
				return ClientMsgInfo.Handshake.parseFrom(data);
			case 8294:
				return ClientMsgInfo.PlayerInfo.parseFrom(data);
			case 8295:
				return ClientMsgInfo.ReqLogin.parseFrom(data);
			case 8296:
				return ClientMsgInfo.ReqRegister.parseFrom(data);
			case 8297:
				return ClientMsgInfo.RetRegister.parseFrom(data);
			case 8298:
				return ClientMsgInfo.RetLogin.parseFrom(data);
			}
		return null;
    }
	
	public static int bytesToInt(byte[] bytes) {
		int value=0;
        for(int i = 0; i < 4; i++) {
            int shift= (3-i) * 8;
            value +=(bytes[i] & 0xFF) << shift;
        }
        return value;
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		//存放消息操作码和消息对象
		Map<Integer,Descriptor> descriptorMap=new HashMap<Integer,Descriptor>();
		//把消息描述对象添加进来
		FileDescriptor descriptor = ClientMsgInfo.getDescriptor();
		List<FileDescriptor> dependencies = descriptor.getDependencies();
		List<FileDescriptor> publicDependencies = descriptor.getPublicDependencies();
		System.out.println(publicDependencies);
		Descriptor descriptor2 = ClientMsgInfo.ReqLogin.getDescriptor();
		System.out.println(descriptor2.getFields());
	}
	

}
