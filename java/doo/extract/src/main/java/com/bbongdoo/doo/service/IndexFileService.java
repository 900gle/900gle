package com.bbongdoo.doo.service;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class IndexFileService {

    public static boolean createFile(String path) {

        File file = new File(path);

        String indexTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

        try {


            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(indexTime);
            writer.close();

            if (file.createNewFile()) {
                System.out.println("File created");
            } else {

                try {
                    Thread.sleep(10000);

                } catch (InterruptedException e) {
                    e.getStackTrace();

                }
                System.out.println("File already exists");
                return false;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

}
