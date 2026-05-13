package com.doo.aqqle.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class StockService {


    public static void main(String[] args) {


        long unixTimestamp = Instant.now().getEpochSecond();

        System.out.println(unixTimestamp);

    }


    public String getStock() {




//        https://query1.finance.yahoo.com/v7/finance/download/VFS?period1=1685621480&period2=1717243880&interval=1d&events=history&includeAdjustedClose=true
//        https://query1.finance.yahoo.com/v7/finance/download/NVDA?period1=917015400&period2=1717244542&interval=1d&events=history&includeAdjustedClose=true
//        https://query1.finance.yahoo.com/v7/finance/download/TSLA?period1=1277818200&period2=1717244467&interval=1d&events=history&includeAdjustedClose=true
        return "";

    }
}
