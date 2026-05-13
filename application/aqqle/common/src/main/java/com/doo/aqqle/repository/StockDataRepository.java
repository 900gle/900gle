package com.doo.aqqle.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface StockDataRepository extends JpaRepository<StockData, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO stock_data_partition (company, company_code, trading_date, open, high, low, close, adj_close, volume) " +
            "VALUES (:company, :companyCode, :tradingDate, :open, :high, :low, :close, :adjClose, :volume) " +
            "ON DUPLICATE KEY UPDATE company = VALUES(company), open = VALUES(open), high = VALUES(high), " +
            "low = VALUES(low), close = VALUES(close), adj_close = VALUES(adj_close), volume = VALUES(volume), " +
            "updated_time = CURRENT_TIMESTAMP", nativeQuery = true)
    void upsert(String company, String companyCode, String tradingDate, Float open, Float high,
                         Float low, Float close, Float adjClose, Long volume);


    Page<StockData> findAllByOrderByIdAsc(Pageable pageable);


}
