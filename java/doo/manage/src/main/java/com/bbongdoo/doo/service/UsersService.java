package com.bbongdoo.doo.service;


import com.bbongdoo.doo.annotation.Timer;
import com.bbongdoo.doo.domain.Users;
import com.bbongdoo.doo.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private Users users;

    @Timer
    @Transactional
    public void post(String name) {
        usersRepository.save(new Users(name, "Y"));
    }

    @Timer
    @Transactional
    public List<Users> get() {
        return usersRepository.findAll();
    }

    @Timer
    @Transactional
    public List<Users> getKeywordsByUse(String use) {
        return usersRepository.findAllByUseYn(use);
    }

    @Timer
    @Transactional
    public List<Users> getKeywordsByKeyword(String keyword) {
        return usersRepository.findAllByName(keyword);
    }



}
