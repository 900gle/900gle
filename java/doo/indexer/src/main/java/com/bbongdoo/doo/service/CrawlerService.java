package com.bbongdoo.doo.service;

import com.bbongdoo.doo.domain.Products;
import com.bbongdoo.doo.domain.ProductsRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CrawlerService {

    @Autowired
    ProductsRepository productsRepository;

    public void getData(String keyword) {

        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000); //1초 대기
                String listUrl = "https://search.shopping.naver.com/search/all.nhn?origQuery=강아지%20샴푸&pagingIndex=" + i + "&pagingSize=40&productSet=model&viewType=list&sort=rel&frm=NVSHMDL&query=" + keyword;

                Document ListDocument = Jsoup.connect(listUrl)
                        .timeout(5000)
                        .get();

                Elements urls = ListDocument.select("div.img_area>a");
                List<String> detailUrl = urls.stream().map(x -> x.attr("abs:href")).collect(Collectors.toList());
                detailUrl.forEach(url -> {

                    try {
                        Thread.sleep(1000); //1초 대기
                        Document document = Jsoup.connect(url)
                                .timeout(5000)
                                .get();

                        Elements title = document.select("div.h_area>h2:not(.blind)");
                        Elements price = document.select("em.num");
                        Elements brand = document.select("div.info_inner>span:first-child>em");
                        Elements category = document.select("span.s_nowrap a");

                        String[] categoryArray = category.text().split(" ");


                        Map<Integer, String> categoryLists = new HashMap<>();

                        for (var j = 0; j < 5; j++) {

                            if (categoryArray.length < 5 && j == 4) {
                                categoryLists.put(4, null);

                            } else {
                                categoryLists.put(j, Optional.ofNullable(categoryArray[j]).orElse(null));

                            }
                        }


                        productsRepository.save(Products.builder()
                                .keyword(keyword)
                                .name(title.text())
                                .price(Integer.parseInt(price.text().replace(",", "")))
                                .brand(brand.text())
                                .category(category.text())
                                .category1(categoryLists.get(0))
                                .category2(categoryLists.get(1))
                                .category3(categoryLists.get(2))
                                .category4(categoryLists.get(3))
                                .category5(categoryLists.get(4))
                                .build()
                        );

//                        Shop shop = Shop.create(title.text(), brand.text(), price.text(), category.text());
//                        new DocumentBulkService().postBulkDocument(shop);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException ie) {

                    }

                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException i) {

        }
    }


}
