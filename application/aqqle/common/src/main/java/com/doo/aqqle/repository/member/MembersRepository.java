package com.doo.aqqle.repository.member;

import com.doo.aqqle.repository.Investments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembersRepository extends JpaRepository<Members, Long> {

    List<Members> findAllByUseYn(String use);

    Page<Members> findAllByOrderByIdAsc(Pageable pageable);


}
