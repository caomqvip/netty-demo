package com.cytxcn.netty.session;

public interface ISessionFactory {
    ISession newSession(Object ... args);

    void releaseSession(ISession session);
}
