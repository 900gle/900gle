package com.doo.aqqle.service;

import com.doo.aqqle.repository.Stock;
import com.doo.aqqle.repository.StockDataRepository;
import com.doo.aqqle.repository.StockRepository;
import com.doo.aqqle.utils.FileDownload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service("YahooService")
public class YahooService extends ExtractService implements AqqleExtract{

    public YahooService (StockRepository stockRepository, StockDataRepository stockDataRepository)
    {
        super(stockRepository, stockDataRepository);
    }
    @Override
    public void execute() {

        List<Stock> stockList = stockRepository.findAllByUseYn("Y");

        stockList.stream().forEach(x-> {

            LocalDate localDate = LocalDate.parse(x.getStartDate());

            long period1 = localDate.atStartOfDay(ZoneId.of("UTC")).toEpochSecond();
            long period2 = Instant.now().getEpochSecond();

            String listUrl = "https://query1.finance.yahoo.com/v7/finance/download/"+x.getCompanyCode()+"?period1="+period1+"&period2="+period2+"&interval=1d&events=history&includeAdjustedClose=true";

            System.out.println(listUrl);

            try {
                FileDownload.downloadFile(listUrl, "/data/stock/");
                System.out.println("파일 다운로드 완료.");
            } catch (IOException e) {
                System.out.println("파일 다운로드 중 오류 발생: " + e.getMessage());
            }
        });
    }
}
