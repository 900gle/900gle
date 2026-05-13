package com.doo.aqqle.event;

public interface AfterTransactionEvent extends AbstractTransactionEvent{
    void completed();
}
