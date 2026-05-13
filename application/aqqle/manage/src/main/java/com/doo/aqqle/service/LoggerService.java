package com.doo.aqqle.service;


import com.doo.aqqle.domain.Dictionarys;
import com.doo.aqqle.domain.DictionarysRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoggerService {

    private final DictionarysRepository dictionarysRepository;
    private List<Dictionarys> dictionarys;


    @Transactional
    public void post(List<String> keywordList) {
        dictionarys = new ArrayList<>();
        keywordList.stream().forEach(x -> {
            dictionarys.add(new Dictionarys(x, "STOP","Y"));
        });
        dictionarysRepository.saveAll(dictionarys);
    }

    @Transactional
    public List<Dictionarys> get() {
        return dictionarysRepository.findAll();
    }

    @Transactional
    public List<Dictionarys> getKeywordsByUse(String use) {
        return dictionarysRepository.findAllByUseYn(use);
    }

    @Transactional
    public List<Dictionarys> getKeywordsByKeyword(String keyword) {
        return dictionarysRepository.findAllByWord(keyword);
    }

    @Transactional
    public boolean put(Long id, String useYn) {
        Dictionarys dictionarys = dictionarysRepository.findAllById(id);
        dictionarys.setUseYn(useYn);
        dictionarysRepository.save(dictionarys);
        return true;
    }

}
