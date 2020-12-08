package com.cytxcn.netty.messages;
 

/**
 * 说明：协议消息头
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月4日 
 */
public class ProtocolHeader{
	private int msgId;		// 序列号
	private int len;		// 长度
	public int getMsgId() {
		return msgId;
	}
	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	public ProtocolHeader() {
		super();
	}
	public ProtocolHeader(int msgId, int len) {
		super();
		this.msgId = msgId;
		this.len = len;
	}
	
	
	
}
