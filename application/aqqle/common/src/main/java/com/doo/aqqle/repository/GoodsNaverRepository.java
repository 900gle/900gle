package com.doo.aqqle.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface GoodsNaverRepository extends JpaRepository<GoodsNaver, Long> {

    List<GoodsNaver> findAllByUpdatedTimeLessThan(@Param("updatedTime") LocalDateTime updatedTime);
    List<GoodsNaver> findAllByUpdatedTimeGreaterThan(@Param("updatedTime") LocalDateTime updatedTime);

    Page<GoodsNaver> findAllByOrderByIdAsc(Pageable pageable);

}
