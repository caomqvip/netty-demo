package com.cytxcn.netty.session.impl;

import io.netty.channel.Channel;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.cytxcn.netty.session.ISession;
import com.cytxcn.netty.session.ISessionFactory;

public class NioSessionFactory implements ISessionFactory {

    private final AtomicInteger ai = new AtomicInteger(1);

    private int generate() {
        return ai.getAndIncrement();
    }

    @Override
    public ISession newSession(Object ... args) {
        if (args.length < 1 || !(args[0] instanceof Channel)) {
            throw new IllegalArgumentException(String.format("not enough args %d , or the first arg is not netty nio channel", args.length));
        }

        Channel channel = (Channel)args[0];
        int sessionId = generate();

        return new NioSocketSession(channel, sessionId);
    }

    @Override
    public void releaseSession(ISession session) {
    }
}
