package com.bbongdoo.doo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordsRepository extends JpaRepository<Keywords, Long> {

    List<Keywords> findAllByUseYn(String use);
    List<Keywords> findAllByKeyword(String Keyword);
    Keywords findAllById(Long id);
}
