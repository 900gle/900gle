package com.doo.aqqle.portal;

import com.doo.aqqle.model.request.LocationRequest;
import com.doo.aqqle.model.request.ShopRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@RequiredArgsConstructor
@Component
@Slf4j
public class PortalEventHandler {

    @Async
    @EventListener
    public void process(ShopRequest shopRequest) {
        log.info("ShopRequest : {}, 시간 : {}", shopRequest, LocalTime.now());
    }

    @Async
    @EventListener
    public void process(LocationRequest locationRequest) {
        log.info("LocationRequest : {}, 시간 : {}", locationRequest, LocalTime.now());
    }
}
