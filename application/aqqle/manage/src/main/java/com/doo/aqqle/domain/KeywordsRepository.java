package com.doo.aqqle.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordsRepository extends JpaRepository<Keywords, Long> {

    List<Keywords> findByUseYn(String use);
    List<Keywords> findByKeyword(String Keyword);
    List<Keywords> findAll();

}
