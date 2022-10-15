package com.bbongdoo.doo.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExceptionServiceTest {

    @Autowired
    private ExceptionService exceptionService;

    @Test
    public void getUserTest(){
        exceptionService.userExp();
    }
}
