package com.bbongdoo.doo.run;

import com.bbongdoo.doo.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppCommondLineRunner implements CommandLineRunner {

    private final GoodsService goodsService;


    @Override
    public void run(String... args) throws Exception {
        goodsService.getData();
    }
}
