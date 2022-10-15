package com.bbongdoo.doo.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    public static String fileDown(String FILE_URL, String OUTPUT_FILE_PATH) throws IOException {

        int limit = FILE_URL.indexOf("?");

        String url = "";
        if (limit > 1) {
             url = FILE_URL.substring(0, FILE_URL.indexOf("?"));
        } else {
             url = FILE_URL;
        }

        File file = new File(url);

        InputStream in = new URL(FILE_URL).openStream();
        Path imagePath = Paths.get(OUTPUT_FILE_PATH+file.getName());
        Files.copy(in, imagePath);

        return OUTPUT_FILE_PATH + file.getName();
    }


}
