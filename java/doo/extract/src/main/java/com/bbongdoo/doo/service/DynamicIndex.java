package com.bbongdoo.doo.service;

import org.springframework.stereotype.Service;

@Service
public class DynamicIndex {

    public boolean startDynamic(){

        IndexFileService.createFile("/data/indextime.txt");

        System.out.println("start");
        return false;
    }

}
