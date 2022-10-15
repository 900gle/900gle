package com.bbongdoo.doo.service;


import com.bbongdoo.doo.annotation.Timer;
import com.bbongdoo.doo.component.TextEmbedding;
import com.bbongdoo.doo.domain.GoodsText;
import com.bbongdoo.doo.domain.GoodsTextRepository;
import com.bbongdoo.doo.domain.Keywords;
import com.bbongdoo.doo.dto.TextEmbeddingDTO;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsTextRepository goodsRepository;
    private final KeywordsService keywordsService;

    private final int CRAWLING_LIMIT = 100;

    @Timer
    public void getData(String type) {

        List<Keywords> keywords = keywordsService.getData();
        keywords.stream().forEach(obj -> {
                    try {
                        int i = 0;
                        while (true) {
                            Thread.sleep(2000); //1초 대기
                            String listUrl = "https://search.shopping.naver.com/search/all?frm=NVSHATC&origQuery=" + obj.getKeyword() + "&pagingIndex=" + i + "&pagingSize=40&productSet=total&query=" + obj.getKeyword() + "&sort=rel&timestamp=&viewType=list";
                            Document listDocument = Jsoup.connect(listUrl)
                                    .timeout(9000)
                                    .get();
                            Elements list = listDocument.select("li.basicList_item__0T9JD");
                            list.stream().forEach(element -> {
                                try {
                                    Elements title = element.select("div.basicList_title__VfX3c>a");
                                    Elements price = element.select("strong.basicList_price__euNoD>span>span.price_num__S2p_v");
                                    Elements category = element.select("div.basicList_depth__SbZWF span");
                                    List<String> categoryLists = category.stream().map(x -> x.text()).collect(Collectors.toList());

                                    Elements image = element.select("a.thumbnail_thumb__Bxb6Z > img");
                                    Thread.sleep(1000);

                                    goodsRepository.save(GoodsText.builder()
                                            .keyword(obj.getKeyword())
                                            .name(title.text())
                                            .price(price.text().equals("") ? 0 : Integer.parseInt(price.text().replaceAll("[^0-9]", "")))
                                            .category(category.text())
                                            .category1(StringUtils.isEmpty(categoryLists.get(0)) ? "" : categoryLists.get(0))
                                            .category2(StringUtils.isEmpty(categoryLists.get(1)) ? "" : categoryLists.get(1))
                                            .category3(StringUtils.isEmpty(categoryLists.get(2)) ? "" : categoryLists.get(2))
                                            .category4(categoryLists.size() < 4 ? "" : categoryLists.get(3))
                                            .category5(categoryLists.size() < 5 ? "" : categoryLists.get(4))
                                            .image(image.attr("src"))
                                            .featureVector(TextEmbedding.getVector(TextEmbeddingDTO.builder().tensorApiUrl("http://localhost:5000/vectors").keyword(title.text()).build()).toString())
                                            .popular(1)
                                            .weight(0.1f)
                                            .type("C")
                                            .createdTime(LocalDateTime.now())
                                            .updatedTime(LocalDateTime.now())
                                            .build()
                                    );

                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            });
                            if (i > CRAWLING_LIMIT) {
                                break;
                            }
                            i++;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException i) {

                    }
//
                    keywordsService.putData(obj);
                }
        );


    }


}
