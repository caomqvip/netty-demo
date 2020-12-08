package com.cytxcn.netty.encoder;


import org.springframework.stereotype.Component;

import com.cytxcn.netty.messages.protobuf.ClientMsgInfo;
import com.cytxcn.netty.util.NetworkUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@Component
public class ProtobufEncoder extends MessageToByteEncoder<Object> {
	
	public ProtobufEncoder() {
		// TODO Auto-generated constructor stub
	}
	

	
	public ProtobufEncoder(boolean preferDirect) {
		super(preferDirect);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
   		 
         out.writeInt(1);//第一位置存放数字1（占用1个字节）
         int a = 8298;
         byte[] int2Bytes_LE = Int2Bytes_LE(a);
         out.writeBytes(int2Bytes_LE);
//         out.writeInt(0);//第二位置存放数字 0（占用2个字节） ，这里是打算做长度
		 
		/* ClientMsgInfo.RetLogin user = ClientMsgInfo.RetLogin.newBuilder().
				 setCode(22).setMsgid(ClientMsgInfo.MsgID.eMsgToCSFromGS_RetLogin).build();
		 
		 byte[] byteArray = user.toByteArray();*/
		 
		 
		 out.writeBytes(((byte[]) msg));//获取字节数组
		 
		 int readableBytes = out.readableBytes();
		 out.setInt(0,NetworkUtil.reverseEndian(readableBytes));//设置第二位置的长度，也就是整个byteBuf对象的长度
	     
	     
	}
	
	
	 public static byte[] Int2Bytes_LE(int iValue){
	        byte[] rst = new byte[4];
	        // 先写int的最后一个字节
	        rst[0] = (byte)(iValue & 0xFF);
	        // int 倒数第二个字节
	        rst[1] = (byte)((iValue & 0xFF00) >> 8 );
	        // int 倒数第三个字节
	        rst[2] = (byte)((iValue & 0xFF0000) >> 16 );
	        // int 第一个字节
	        rst[3] = (byte)((iValue & 0xFF000000) >> 24 );
	        return rst;
	    }
	
	
}
