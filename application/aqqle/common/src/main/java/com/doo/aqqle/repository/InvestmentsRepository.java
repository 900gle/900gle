package com.doo.aqqle.repository;

import com.doo.aqqle.domain.AqqleGoods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestmentsRepository extends JpaRepository<Investments, Long> {

    List<Investments> findAllByUseYn(String use);

    Page<Investments> findAllByOrderByIdAsc(Pageable pageable);


}
