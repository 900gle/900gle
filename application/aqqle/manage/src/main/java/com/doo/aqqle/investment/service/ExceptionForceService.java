package com.doo.aqqle.investment.service;

import com.doo.aqqle.advice.exception.CApiFailException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ExceptionForceService {
    public static void databaseCall() throws RuntimeException {
        throw new RuntimeException();
    }

    public static void exceptionExec() throws CApiFailException {
        try {
            databaseCall();
        } catch (RuntimeException e) {
            throw new CApiFailException("DB 연결 실패", e); // 예외를 던짐

        }
    }
}
