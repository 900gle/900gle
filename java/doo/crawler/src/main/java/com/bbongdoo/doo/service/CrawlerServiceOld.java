package com.bbongdoo.doo.service;


import com.bbongdoo.doo.component.ImageToVectorOpenCV;
import com.bbongdoo.doo.domain.Products;
import com.bbongdoo.doo.domain.ProductsRepository;
import com.bbongdoo.doo.dto.CrawlerDto;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrawlerServiceOld {

    private final ProductsRepository productsRepository;

    public void getData(CrawlerDto crawlerDto) {

        try {
            for (int i = 0; i < 100; i++) {
                Thread.sleep(2000); //1초 대기

                String listUrl = "https://search.shopping.naver.com/search/all.nhn?origQuery=" + crawlerDto.getKeyword() + "&pagingIndex=" + i + "&pagingSize=40&productSet=model&viewType=list&sort=rel&frm=NVSHMDL&query=" + crawlerDto.getKeyword();

                Document listDocument = Jsoup.connect(listUrl)
                        .timeout(5000)
                        .get();

                Elements urls = listDocument.select("div.thumbnail_thumb_wrap__1pEkS>a");

                List<String> detailUrl = urls
                        .stream()
                        .map(x -> x.attr("abs:href"))
                        .collect(Collectors.toList());

                detailUrl.forEach(url -> {
                    try {
                        Thread.sleep(1000); //1초 대기

                        System.out.println(url);
                        Document document = Jsoup.connect(url)
                                .timeout(5000)
                                .get();


                        Elements title = document.select("div.top_summary_title__15yAr>h2");
                        Elements price = document.select("em.lowestPrice_num__3AlQ-");
                        Elements brand = document.select("div.info_inner>span:first-child>em");
                        Elements category = document.select("div.top_breadcrumb__yrBH6 a");
                        Elements image = document.select("div.image_thumb__20xyr>img");

                        String[] categoryArray = category.text().split(" ");

                        Map<Integer, String> categoryLists = new HashMap<>();
                        for (var j = 0; j < categoryArray.length; j++) {
                            categoryLists.put(j, Optional.ofNullable(categoryArray[j]).orElse(null));
                        }

                        productsRepository.save(Products.builder()
                                .keyword(crawlerDto.getKeyword())
                                .name(title.text())
                                .price(price.text().equals("") ? 0 : Integer.parseInt(price.text().replace(",", "")))
                                .brand(brand.text())
                                .category(category.text())
                                .category1(categoryLists.get(0))
                                .category2(categoryLists.get(1))
                                .category3(categoryLists.get(2))
                                .category4(categoryLists.get(3))
                                .category5(categoryLists.get(4))
                                .image(image.attr("src"))
                                .imageVector(ImageToVectorOpenCV.getVector(image.attr("src")).toString())
                                .type(null)
                                .build()
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException i) {

        }
    }


}
