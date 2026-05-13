package com.doo.aqqle.utils;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class FileUtils {


    public static void main(String[] args) {

       String dirName =  files("/data/static");

        System.out.println("dirName : " + dirName);

    }

    public static String files(String path) {

        File file = new File(path);

        String[] filenames = file.list();

        Arrays.stream(filenames).forEach(x-> System.out.println(x));



        String fileName = Arrays.stream(filenames).sorted(Comparator.reverseOrder()).findFirst().toString();

        return fileName;

    }

}
