package com.bbongdoo.doo.sample;


import com.bbongdoo.doo.component.ImageToVectorOpenCV;
import org.opencv.core.Core;

import java.io.IOException;
import java.util.Vector;

public class ImageSIFTOpenCV {

    public static void main(String[] args) {
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);





        String filePath = "https://shopping-phinf.pstatic.net/main_2698255/26982557658.20210430181209.jpg?type=f640";
        try {
//            Vector<Double> getVector = ImageToVectorOpenCV.getVector(filePath);
            ImageToVectorOpenCV.getVector(filePath);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

