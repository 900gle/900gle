package com.bbongdoo.doo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface GoodsTextRepository extends JpaRepository<GoodsText, Long> {

    List<GoodsText> findAllByUpdatedTimeLessThan(@Param("updatedTime") LocalDateTime updatedTime);
    List<GoodsText> findAllByUpdatedTimeGreaterThan(@Param("updatedTime") LocalDateTime updatedTime);
}
