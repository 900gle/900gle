package com.bbongdoo.doo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    List<Goods> findAllByUpdatedTimeLessThan(@Param("updatedTime") LocalDateTime updatedTime);
    List<Goods> findAllByUpdatedTimeGreaterThan(@Param("updatedTime") LocalDateTime updatedTime);
}
