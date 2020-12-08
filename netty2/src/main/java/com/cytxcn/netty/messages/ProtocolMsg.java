package com.cytxcn.netty.messages;

/**
 * 说明：消息对象
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月5日 
 */
public class ProtocolMsg {
	 
	private ProtocolHeader protocolHeader = new ProtocolHeader();
 	private Object body;
 	
	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}


	/**
	 * 
	 */
	public ProtocolMsg() {
		// TODO Auto-generated constructor stub
	}
	
	public ProtocolHeader getProtocolHeader() {
		return protocolHeader;
	}

	public void setProtocolHeader(ProtocolHeader protocolHeader) {
		this.protocolHeader = protocolHeader;
	}


}
