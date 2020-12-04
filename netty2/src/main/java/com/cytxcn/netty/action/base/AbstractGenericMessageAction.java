package com.cytxcn.netty.action.base;


public abstract class AbstractGenericMessageAction implements IMessageAction {

    protected int id;

    public AbstractGenericMessageAction() {
        this.id = 0;
    }


    @Override
    public Integer getId() {
        return id;
    }
}
