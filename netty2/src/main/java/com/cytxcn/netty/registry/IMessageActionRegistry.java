package com.cytxcn.netty.registry;

import com.cytxcn.netty.action.base.IMessageAction;

public interface IMessageActionRegistry<T> {
    IMessageAction get(T command);

    void add(T command, IMessageAction handler);

    void remove(T command);

    boolean contain(T command);

    void release();
}
