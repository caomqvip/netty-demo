package com.cytxcn.netty.action.base;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cytxcn.netty.session.ISessionService;

import io.netty.channel.ChannelHandlerContext;
public abstract class AbstractBaseMessageAction<T> extends AbstractGenericMessageAction {

	
	
    public AbstractBaseMessageAction() {
        super();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void processMessage(ChannelHandlerContext ctx, Object req) throws Exception {
        T msg = (T)req;
        process(ctx, getConnId(ctx, msg), msg);
    }

    public void process(ChannelHandlerContext ctx, int connId, T req) throws Exception {
        process(connId, req);
    }

    public abstract int getConnId(ChannelHandlerContext ctx, T msg);

    public abstract T newObject();

    public abstract void process(int connId, T req) throws Exception;

	
    
    
    
    
}
