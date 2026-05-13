package com.doo.aqqle.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

//@NoRepositoryBean
public interface GoodsRepository extends JpaRepository<Goods, Long> {

    List<Goods> findAllByUpdatedTimeLessThan(@Param("updatedTime") LocalDateTime updatedTime);

    List<Goods> findAllByUpdatedTimeGreaterThan(@Param("updatedTime") LocalDateTime updatedTime);

    Page<Goods> findAllByOrderByIdAsc(Pageable pageable);
}
