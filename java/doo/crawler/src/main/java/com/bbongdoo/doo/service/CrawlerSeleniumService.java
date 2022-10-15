package com.bbongdoo.doo.service;


import com.bbongdoo.doo.component.ImageToVectorOpenCV;
import com.bbongdoo.doo.component.TextEmbedding;
import com.bbongdoo.doo.domain.*;
import com.bbongdoo.doo.dto.CrawlerDto;
import com.bbongdoo.doo.dto.TextEmbeddingDTO;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrawlerSeleniumService {

    private final GoodsTextRepository goodsRepository;
    private final KeywordsService keywordsService;

    private final int CRAWLING_LIMIT = 100;

    public void getData(String type) {

        List<Keywords> keywords = keywordsService.getData();

        System.setProperty("webdriver.chrome.driver", "/Users/doo/bin/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");            // 전체화면으로 실행
//        options.addArguments("--window-size= 200, 200"); //실행되는 브라우저 크기를 지정할 수 있습니다. - 이것도 안먹히는데
//        options.addArguments("--start-fullscreen");            // 브라우저가 풀스크린 모드(F11)로 실행됩니다.
        options.addArguments("--disable-popup-blocking");    // 팝업 무시
        options.addArguments("--disable-default-apps");     // 기본앱 사용안함
//        options.addArguments("user-agent=" + userAgent);
        options.addArguments("--blink-settings=imagesEnabled=false");            // 브라우저에서 이미지 로딩을 하지 않습니다.
        options.addArguments("--mute-audio");            // 브라우저에 음소거 옵션을 적용합니다.
        options.addArguments("incognito");            // 시크릿 모드의 브라우저가 실행됩니다.


        ChromeDriver driver = new ChromeDriver(options);


        driver.executeScript("window.open('about:blank','_blank');");
        List<String> tabs = new ArrayList<String>(driver.getWindowHandles());


        driver.switchTo().window(tabs.get(0));


        int i = 0;
        for (Keywords keys : keywords) {

            String key = keys.getKeyword();
            String listUrl = "https://search.shopping.naver.com/search/all?frm=NVSHATC&origQuery=" + key + "&pagingIndex=" + i + "&pagingSize=40&productSet=total&query=" + key + "&sort=rel&timestamp=&viewType=list";

            // 웹페이지 요청
            driver.get(listUrl);


            try {

                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.getStackTrace();
            }


            List<WebElement> muchoCheese = driver.findElements(By.cssSelector("li.basicList_item__0T9JD"));

            muchoCheese.stream().forEach(e -> {

                String imageUrl = "";
                if (e.findElement(By.cssSelector("a.thumbnail_thumb__Bxb6Z > img")).isEnabled()) {
                    imageUrl = e.findElement(By.cssSelector("a.thumbnail_thumb__Bxb6Z > img")).getAttribute("src");
                }

                String title = e.findElement(By.cssSelector("div.basicList_title__VfX3c>a")).getText();
                String price = e.findElement(By.cssSelector("strong.basicList_price__euNoD>span>span.price_num__S2p_v")).getText();
                List<WebElement> categorys = e.findElements(By.cssSelector("div.basicList_depth__SbZWF span"));
                List<String> categoryLists = categorys.stream().map(x -> x.getText()).collect(Collectors.toList());

                try {
                    goodsRepository.save(GoodsText.builder()
                                    .keyword(key)
                                    .name(title)
                                    .price(price.equals("") ? 0 : Integer.parseInt(price.replaceAll("[^0-9]", "")))
//                        .category(categorys)
                                    .category1(StringUtils.isEmpty(categoryLists.get(0)) ? "" : categoryLists.get(0))
                                    .category2(StringUtils.isEmpty(categoryLists.get(1)) ? "" : categoryLists.get(1))
                                    .category3(StringUtils.isEmpty(categoryLists.get(2)) ? "" : categoryLists.get(2))
                                    .category4(categoryLists.size() < 4 ? "" : categoryLists.get(3))
                                    .category5(categoryLists.size() < 5 ? "" : categoryLists.get(4))
                                    .image(imageUrl)
                                    .featureVector(TextEmbedding.getVector(TextEmbeddingDTO.builder().tensorApiUrl("http://localhost:5000/vectors").keyword(title).build()).toString())
                                    .popular(1)
                                    .weight(0.1f)
                                    .type("C")
                                    .createdTime(LocalDateTime.now())
                                    .updatedTime(LocalDateTime.now())
                                    .build()
                    );

                } catch (IOException ie) {
                    ie.printStackTrace();
                }

            });

            i++;
        }


    }


}
