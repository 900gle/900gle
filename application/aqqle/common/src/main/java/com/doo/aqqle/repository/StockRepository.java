package com.doo.aqqle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findAllByUseYn(String use);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO stock (company, company_code, exchange, start_date, end_date, `type`, use_yn) " +
            "VALUES (:company, :companyCode, :exchange, :startDate, :endDate, :type, :useYn) " +
            "ON DUPLICATE KEY UPDATE " +
            "company = :company, " +
            "exchange = :exchange, " +
            "start_date = :startDate, " +
            "end_date = :endDate, " +
            "`type` = :type, " +
            "use_yn = :useYn", nativeQuery = true)
    void upsert(
            @Param("company") String company,
            @Param("companyCode") String companyCode,
            @Param("exchange") String exchange,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("type") String type,
            @Param("useYn") String useYn
    );
}
