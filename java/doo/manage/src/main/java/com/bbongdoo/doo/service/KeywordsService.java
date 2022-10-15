package com.bbongdoo.doo.service;


import com.bbongdoo.doo.annotation.Timer;
import com.bbongdoo.doo.domain.Keywords;
import com.bbongdoo.doo.domain.KeywordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeywordsService {

    private final KeywordsRepository keywordsRepository;
    private List<Keywords> keywords;

    @Timer
    @Transactional
    public void post(List<String> keywordList) {
        keywords = new ArrayList<>();
        keywordList.stream().forEach(x -> {
            keywords.add(new Keywords(x, "Y"));
        });
        keywordsRepository.saveAll(keywords);
    }

    @Timer
    @Transactional
    public List<Keywords> get() {
        return keywordsRepository.findAll();
    }

    @Timer
    @Transactional
    public List<Keywords> getKeywordsByUse(String use) {
        return keywordsRepository.findAllByUseYn(use);
    }

    @Timer
    @Transactional
    public List<Keywords> getKeywordsByKeyword(String keyword) {
        return keywordsRepository.findAllByKeyword(keyword);
    }

    @Timer
    @Transactional
    public boolean put(Long id, String useYn) {
        Keywords keywords = keywordsRepository.findAllById(id);
        keywords.setUseYn(useYn);
        keywordsRepository.save(keywords);
        return true;
    }

}
