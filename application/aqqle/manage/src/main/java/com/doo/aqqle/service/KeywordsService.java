package com.doo.aqqle.service;


import com.doo.aqqle.annotation.Timer;
import com.doo.aqqle.domain.Keywords;
import com.doo.aqqle.domain.KeywordsRepository;
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
        return keywordsRepository.findByUseYn(use);
    }

    @Timer
    @Transactional
    public List<Keywords> getKeywordsByKeyword(String keyword) {
        return keywordsRepository.findByKeyword(keyword);
    }

    @Timer
    @Transactional
    public boolean put(Long id, String useYn) {
        Keywords keywords = keywordsRepository.findById(id).orElse(null);
        if (keywords != null) {
            keywords.setUseYn(useYn);
            if (keywordsRepository.save(keywords) != null){
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

}
