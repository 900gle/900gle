package com.bbongdoo.doo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class IndexFileService {


    public static boolean createDirectory(String path){

        File folder = new File(path);
        if (!folder.exists()) {
            try{
                folder.mkdirs(); //폴더 생성합니다.
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }else {
            folder.delete();
            folder.mkdirs(); //폴더 생성합니다.
        }

       return true;
    }

    public static boolean createFile(String path, String data) {

        File file = new File(path);
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(data);
            writer.close();

            if (file.createNewFile()) {
                System.out.println("File created");
            } else {
//                System.out.println("File already exists");
                return false;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

}
