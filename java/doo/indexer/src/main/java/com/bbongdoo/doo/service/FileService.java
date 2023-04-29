package com.bbongdoo.doo.service;

import java.io.File;
import java.util.Arrays;

public class FileService {

    public static void main(String[] args) {

        String dir = "/data/static";
        File file = new File(dir);



        Arrays.stream(file.listFiles()).forEach(x-> System.out.println(x));


//        Arrays.stream(file.list()).forEach(x-> System.out.println(x));



    }

}
