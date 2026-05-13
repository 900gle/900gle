package com.doo.aqqle.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
public class KeywordEventHandler {


    @Async
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void beforTransactionProcess(BeforeTransactionEvent beforeTransactionEvent){
        beforeTransactionEvent.callback();
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterTransactionProcess(AfterTransactionEvent afterTransactionEvent){
        afterTransactionEvent.callback();
    }
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void compleatedTransactionProcess(AfterTransactionEvent afterTransactionEvent){
        afterTransactionEvent.completed();
    }
}
