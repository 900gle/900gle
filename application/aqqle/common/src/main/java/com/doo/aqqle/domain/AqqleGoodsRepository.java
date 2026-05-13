package com.doo.aqqle.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AqqleGoodsRepository extends JpaRepository<AqqleGoods, Long> {

    List<AqqleGoods> findAllByUpdatedTimeLessThan(@Param("updatedTime") LocalDateTime updatedTime);
    List<AqqleGoods> findAllByUpdatedTimeGreaterThan(@Param("updatedTime") LocalDateTime updatedTime);

    Page<AqqleGoods> findAllByOrderByIdAsc(Pageable pageable);

}
