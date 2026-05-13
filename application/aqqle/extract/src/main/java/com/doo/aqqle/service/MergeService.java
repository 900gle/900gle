package com.doo.aqqle.service;

import com.doo.aqqle.repository.GoodsNaver;
import com.doo.aqqle.repository.GoodsNaverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MergeService {

    private final GoodsNaverRepository naverGoodsRepository;


    public void index() {

        Page<GoodsNaver> list =  naverGoodsRepository.findAllByOrderByIdAsc(Pageable.ofSize(1));

        list.stream().forEach(x->{
            System.out.println(x.getName());
        });

    }

}
