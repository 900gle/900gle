package com.bbongdoo.doo.service;

import com.bbongdoo.doo.advice.exception.CUserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ExceptionService {

    public void userExp() {
        new CUserNotFoundException();
    }
}
