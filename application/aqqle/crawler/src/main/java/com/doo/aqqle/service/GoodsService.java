package com.doo.aqqle.service;


import com.doo.aqqle.HostUrl;
import com.doo.aqqle.annotation.Timer;
import com.doo.aqqle.component.ElementComponent;
import com.doo.aqqle.component.TextEmbedding;
import com.doo.aqqle.domain.Keywords;
import com.doo.aqqle.dto.TextEmbeddingDTO;
import com.doo.aqqle.element.Site;
import com.doo.aqqle.repository.GoodsNaver;
import com.doo.aqqle.repository.GoodsNaverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsNaverRepository goodsRepository;
    private final KeywordsService keywordsService;

    private final int CRAWLING_LIMIT = 100;

    @Timer
    public void getData(String type) {

        Site site = ElementComponent.getElementSite(type);



        List<Keywords> keywords = keywordsService.getData();

        keywords.stream().forEach(obj -> {

                    try {
                        keywordsService.putData(obj.getId());

                        int i = 0;
                        while (true) {
                            Thread.sleep(3000); //1초 대기
                            String listUrl = site.getUrl(obj.getKeyword(), i);
                            System.out.println(listUrl);
                            Document listDocument = Jsoup.connect(listUrl)
                                    .timeout(9000)
                                    .get();

                            Elements list = listDocument.select("div.adProduct_inner__W_nuz");
                            log.info("List Size : {}", list.size());
                            list.stream().forEach(element -> {
                                try {
                                    Elements title = element.select("div.adProduct_title__amInq>a");
                                    Elements price = element.select("strong.adProduct_price__9gODs>span>span.price_num__S2p_v");
                                    Elements category = element.select("div.adProduct_depth__s_IUT span");
                                    List<String> categoryLists = category.stream().map(x -> x.text()).collect(Collectors.toList());

                                    Elements image = element.select("a.thumbnail_thumb__Bxb6Z > img");

                                    System.out.println(image.attr("src"));

                                    Thread.sleep(1000);

                                    goodsRepository.save(GoodsNaver.builder()
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
                                            .featureVector(TextEmbedding.getVector(TextEmbeddingDTO.builder().tensorApiUrl(HostUrl.EMBEDDING.getUrl()).keyword(title.text()).build()).toString())
                                            .popular(1)
                                            .weight(0.1f)
                                            .type("N")
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

                            if (list.size() < 1) {
                                i = 1;
                            }

                            i++;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException i) {
                        i.printStackTrace();
                    }
                }
        );
    }


}
