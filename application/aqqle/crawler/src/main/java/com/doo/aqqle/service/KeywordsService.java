package com.doo.aqqle.service;


import com.doo.aqqle.annotation.Timer;
import com.doo.aqqle.domain.Keywords;
import com.doo.aqqle.domain.KeywordsRepository;
import com.doo.aqqle.event.AfterTransactionEvent;
import com.doo.aqqle.event.BeforeTransactionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeywordsService {

    private final KeywordsRepository keywordsRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    @Timer
    public List<Keywords> getData() {
        return keywordsRepository.findByUseYn("Y");
    }

    @Transactional
    @Timer
    public void putData(Long id) {

        Keywords keywords = keywordsRepository.findById(id).orElse(null);
        if (keywords != null) {
            keywords.setUseYn("N");
            keywordsRepository.save( keywords);
        }

        BeforeTransactionEvent beforeTransactionEvent = () -> log.info("commit 시작 {}", LocalTime.now());
        AfterTransactionEvent afterTransactionEvent = new
                AfterTransactionEvent() {
                    @Override
                    public void completed() {
                        log.info("트랜잭션 끝 : {}", LocalTime.now());

                    }

                    @Override
                    public void callback() {
                        log.info("commit 종료 : {}", LocalTime.now());

                    }
                };

        applicationEventPublisher.publishEvent(beforeTransactionEvent);
        applicationEventPublisher.publishEvent(afterTransactionEvent);
    }


}
