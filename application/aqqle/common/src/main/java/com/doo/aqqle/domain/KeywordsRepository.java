package com.doo.aqqle.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KeywordsRepository extends JpaRepository<Keywords, Long> {

    List<Keywords> findByUseYn(String use);
    List<Keywords> findByKeyword(String Keyword);
    List<Keywords> findAll();
    Keywords save(Keywords keywords);
}
