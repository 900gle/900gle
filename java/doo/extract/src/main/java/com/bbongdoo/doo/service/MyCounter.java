package com.bbongdoo.doo.service;

import org.springframework.stereotype.Service;

@Service
public class MyCounter {
    private int count;

    public void increment() {
        try {
            int temp = count;
            count = temp + 1;
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        return count;
    }
}
