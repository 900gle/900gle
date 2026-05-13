package com.doo.aqqle.service;


import com.doo.aqqle.element.Site;
import com.doo.aqqle.factory.SiteFactory;
import com.doo.aqqle.factory.YahooDataFactory;
import com.doo.aqqle.repository.Stock;
import com.doo.aqqle.repository.StockData;
import com.doo.aqqle.repository.StockDataRepository;
import com.doo.aqqle.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service("YahooDataService")
public class YahooDataService extends AqqleService implements AqqleCrawler {

    public YahooDataService(
            StockRepository stockRepository,
            StockDataRepository stockDataRepository) {
        super(stockRepository, stockDataRepository);

    }

    private final int CRAWLING_COUNT = 25;

    @Override
    public void execute() {

        List<Stock> stockList = stockRepository.findAllByUseYn("Y");

        Site site = SiteFactory.getSite(new YahooDataFactory());

        stockList.stream().forEach(x -> {

            LocalDate localDate = LocalDate.parse(x.getStartDate());
            long period1 = localDate.atStartOfDay(ZoneId.of("UTC")).toEpochSecond();
            long period2 = Instant.now().getEpochSecond();

            Map<String, Long> periodMap = new HashMap<>();
            periodMap.put("period1", period1);
            periodMap.put("period2", period2);

            site.getUrl(x.getCompanyCode(), periodMap);

            String listUrl = site.getUrl(x.getCompanyCode(), periodMap);

            System.out.println(listUrl);
            try {
                Document listDocument = Jsoup.connect(listUrl)
                        .timeout(5000)
                        .get();
                Elements tableTr = listDocument.select(site.getListCssSelector());

                tableTr.stream()
                        .map(tr -> tr.select("td").stream()
                                .map(Element::text)  // 각 td 요소의 텍스트를 추출
                                .collect(Collectors.toList())  // 추출한 텍스트를 리스트로 수집
                        )
                        .filter(tds -> tds.size() > 6)
                        .forEach(tdTexts -> {

                            StockData stockData = new StockData();
                            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
                            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate date = LocalDate.parse(tdTexts.get(0), inputFormatter);

                            // 날짜를 원하는 형식으로 포맷
                            String tradingDate = date.format(outputFormatter);

                            stockData.setCompany(x.getCompany());
                            stockData.setCompanyCode(x.getCompanyCode());
                            stockData.setTradingDate(tradingDate);  // 첫 번째 항목: 거래일
                            stockData.setOpen(Float.parseFloat(tdTexts.get(1).replace(",", "")));         // 두 번째 항목: 시가
                            stockData.setHigh(Float.parseFloat(tdTexts.get(2).replace(",", "")));         // 세 번째 항목: 최고가
                            stockData.setLow(Float.parseFloat(tdTexts.get(3).replace(",", "")));          // 네 번째 항목: 최저가
                            stockData.setClose(Float.parseFloat(tdTexts.get(4).replace(",", "")));        // 다섯 번째 항목: 종가
                            stockData.setAdjClose(Float.parseFloat(tdTexts.get(5).replace(",", "")));     // 여섯 번째 항목: 수정 종가


                            Long volume = 0L;
                            if(!tdTexts.get(6).replace(",", "").replace("-", "").isBlank()) {
                                volume = Long.parseLong(tdTexts.get(6).replace(",", "").replace("-", ""));
                            }
                            stockData.setVolume(volume);       // 일곱 번째 항목: 거래량

                            stockDataRepository.upsert(
                                    stockData.getCompany(),
                                    stockData.getCompanyCode(),
                                    stockData.getTradingDate(),
                                    stockData.getOpen(),
                                    stockData.getHigh(),
                                    stockData.getLow(),
                                    stockData.getClose(),
                                    stockData.getAdjClose(),
                                    stockData.getVolume()
                            );
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("DATA 저장 완료.");
        });
    }

}

