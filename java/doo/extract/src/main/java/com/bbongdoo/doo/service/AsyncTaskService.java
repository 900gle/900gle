package com.bbongdoo.doo.service;

import com.bbongdoo.doo.domain.Goods;
import com.bbongdoo.doo.domain.GoodsRepository;
import com.bbongdoo.doo.domain.GoodsText;
import com.bbongdoo.doo.domain.GoodsTextRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncTaskService {

    private final GoodsTextRepository goodsTextRepository;

    @Async("executor")
    public CompletableFuture<Integer> task(int i) {


        PageRequest pageRequest = PageRequest.of(0, 5);

        Page<GoodsText> goodsTexts = goodsTextRepository.findAll(pageRequest);


        goodsTexts.stream().forEach(
                x -> {

                    System.out.println(x.getName());
                }
        );

        return CompletableFuture.supplyAsync(goodsTexts::getSize);
    }

}
