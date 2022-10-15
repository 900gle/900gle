package com.bbongdoo.doo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductsRepository extends JpaRepository<Products, Long> {

    List<Products> findAllByUpdatedTimeLessThan(@Param("updatedTime") LocalDateTime updatedTime);
    List<Products> findAllByUpdatedTimeGreaterThan(@Param("updatedTime") LocalDateTime updatedTime);



}
