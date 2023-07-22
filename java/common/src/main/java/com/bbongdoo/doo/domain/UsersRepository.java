package com.bbongdoo.doo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {

    List<Users> findAllByUseYn(String use);
    List<Users> findAllByName(String Keyword);
    Users findAllById(Long id);
}
