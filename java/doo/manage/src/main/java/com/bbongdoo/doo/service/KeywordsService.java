package com.bbongdoo.doo.service;


import com.bbongdoo.doo.domain.Keywords;
import com.bbongdoo.doo.domain.KeywordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordsService {

    private final KeywordsRepository keywordsRepository;
    List<Keywords> keywords;

    public void post(List<String> keywordList) {
        keywords = new ArrayList<>();

        System.out.println(keywordList);

        keywordList.stream().forEach(x -> {
            System.out.println(x);
        });

        keywordList.stream().forEach(x -> {
            keywords.add(new Keywords(x, "Y"));
        });
        keywordsRepository.saveAll(keywords);
    }
}
