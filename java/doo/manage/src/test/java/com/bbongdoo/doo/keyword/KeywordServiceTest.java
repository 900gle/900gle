package com.bbongdoo.doo.keyword;

import com.bbongdoo.doo.domain.Keywords;
import com.bbongdoo.doo.domain.KeywordsRepository;
import com.bbongdoo.doo.service.KeywordsService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@SpringBootTest
@Transactional
public class KeywordServiceTest {

    @Autowired
    private KeywordsService keywordsService;

    @Autowired
    private KeywordsRepository keywordsRepository;

    private int count = 10;
    List<Keywords> keywords;
    List<String> names;

    @BeforeEach
    void setUp() {
        keywords = LongStream.range(1, count + 1)
                .mapToObj(number -> new Keywords("에르디아" + number, "Y"))
                .collect(Collectors.toList());
        names = keywords.stream()
                .map(Keywords::getKeyword)
                .collect(Collectors.toList());

        System.out.println("---------------insert---------------");
        keywordsRepository.saveAll(keywords);
    }

    @Test
    void findAllTest() {
        System.out.println("---------------findAll---------------");
        List<Keywords> keywords = keywordsService.get();

        Assertions.assertThat(keywords.size()).isEqualTo(count);
    }

    @Test
    void findAllByUseTest() {
        System.out.println("---------------findAllByUse---------------");
        List<Keywords> keywordsByUse = keywordsService.getKeywordsByUse("Y");
        Assertions.assertThat(keywordsByUse.size()).isEqualTo(count);
    }

    @Test
    void findAllByKeywordTest() {
        System.out.println("---------------findAllByKeyword---------------");
        List<Keywords> keywordsByKeyword = keywordsService.getKeywordsByKeyword("에르디아1");
        Assertions.assertThat(keywordsByKeyword.size()).isGreaterThan(0);
    }




}
