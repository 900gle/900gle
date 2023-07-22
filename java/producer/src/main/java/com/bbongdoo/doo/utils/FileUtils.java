package com.bbongdoo.doo.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils {


    public static boolean folerMkdir(String path) throws IOException {
        File Folder = new File(path);
        if (!Folder.exists()) {
            Folder.mkdir();
            System.out.println("폴더가 생성되었습니다.");
        } else {
            System.out.println("이미 폴더가 생성되어 있습니다.");
            return false;
        }
        return true;
    }
}
