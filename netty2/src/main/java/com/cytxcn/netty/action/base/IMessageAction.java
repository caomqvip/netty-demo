package com.cytxcn.netty.action.base;

import io.netty.channel.ChannelHandlerContext; 

public interface IMessageAction {
    Integer getId();

    void processMessage(ChannelHandlerContext ctx, Object object) throws Exception;
}
