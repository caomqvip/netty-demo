package com.cytxcn.netty.session.impl;

import io.netty.channel.Channel; 

import java.net.InetSocketAddress;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.cytxcn.netty.session.ISession;

public class NioSocketSession implements ISession {
    private final int connId;
    private int userId;
    private Date loginTime;
    private final Channel channel;
    private final Date createTime;
    private long lastRecvTime = System.currentTimeMillis();
    private long recvBytes = 0;
    private long sendBytes = 0;

    public NioSocketSession(Channel channel, int connId) {
        this.channel = channel;
        this.connId = connId;
        this.createTime = new Date();
    }

    @Override
    public int getId() {
        return connId;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public Date getLoginTime() {
        return loginTime;
    }

    @Override
    public Object getChannelId() {
        return channel.id();
    }

    @Override
    public long getRecvBytes() {
        return recvBytes;
    }

    @Override
    public long getSendBytes() {
        return sendBytes;
    }

    @Override
    public String getAddressInfo() {
        InetSocketAddress addr = ((InetSocketAddress) channel.remoteAddress());
        return String.format("%s:%d", addr.getAddress().getHostAddress(), addr.getPort());
    }

    @Override
    public long getLastRecvTimestamp() {
        return lastRecvTime;
    }

    @Override
    public void login(int userId) {
        this.userId = userId;
        this.loginTime = new Date();
    }

    @Override
    public int logout() {
        int userId = this.userId;
        this.userId = 0;
        this.loginTime = null;
        return userId;
    }

    @Override
    public void onRecv(Object obj) {
        /*lastRecvTime = System.currentTimeMillis();

        if (obj instanceof IMessage) {
            IMessage msg = (IMessage)obj;
            this.recvBytes += msg.getTotalLength();
        }*/
    }

    @Override
    public void write(Object obj) {
        channel.writeAndFlush(obj);
    }

    @Override
    public void close() {
        lastRecvTime = 0;
        if (channel != null) {
            channel.close();
        }
    }
}
