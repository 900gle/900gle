package com.doo.aqqle.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TmonGoodsRepository extends JpaRepository<TmonGoods, Long> {

    List<TmonGoods> findAllByUpdatedTimeLessThan(@Param("updatedTime") LocalDateTime updatedTime);
    List<TmonGoods> findAllByUpdatedTimeGreaterThan(@Param("updatedTime") LocalDateTime updatedTime);

    Page<TmonGoods> findAllByOrderByIdAsc(Pageable pageable);

}
