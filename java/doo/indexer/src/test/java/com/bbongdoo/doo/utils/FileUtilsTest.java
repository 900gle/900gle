package com.bbongdoo.doo.utils;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@SpringBootTest
@Transactional
public class FileUtilsTest {


    @Test
    public void FolderMkdirTest(){
        String path = "./data";

        try {
            FileUtils.folerMkdir(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
