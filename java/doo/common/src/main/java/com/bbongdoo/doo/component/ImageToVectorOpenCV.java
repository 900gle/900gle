package com.bbongdoo.doo.component;

import com.bbongdoo.doo.dto.VectorDTO;
import com.bbongdoo.doo.utils.FileUtil;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.SIFT;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class ImageToVectorOpenCV {

    public static Vector<Double> getVector(VectorDTO vectorDTO) throws IOException {
        String filePath = vectorDTO.getDirPath() + vectorDTO.getFile().getOriginalFilename();
        vectorDTO.getFile().transferTo(new File(filePath));
        return getDoubleVector(getImageDiscripter(filePath));
    }

    public static Vector<Double> getVector(String filePath) throws IOException {
        String imageDirectory = "/Users/doo/project/doo/web/src/main/resources/static/images/";
        String path = FileUtil.fileDown(filePath, imageDirectory);
        return getDoubleVector(getImageDiscripter(path));
    }

    private static Mat getImageDiscripter(String filePath){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = Imgcodecs.imread(filePath);

        MatOfKeyPoint keyPointOfImages = new MatOfKeyPoint();
        SIFT.create().detect(image, keyPointOfImages);

        Mat discripters = new Mat();
        Mat mask = new Mat();
        SIFT.create().detectAndCompute(image, mask, keyPointOfImages, discripters);
        return discripters;
    }

    private static Vector<Integer> getIntVector(Mat discripters) {
        Vector<Integer> integerVector = new Vector<>();
        for (int i = 0; i < discripters.size(1); i++) {
            Vector<Boolean> booleanVector = new Vector<>();
            for (int j = 0; j < discripters.size(0); j++) {
                booleanVector.add(discripters.get(j, i)[0] % 2 > 0);
            }
            int hashNum = booleanVector.hashCode();
            integerVector.add(hashNum);
        }
        return integerVector;
    }

    private static Vector<Double> getDoubleVector(Mat discripters) {
        Vector<Double> doubleVector = new Vector<>();
        for (int i = 0; i < discripters.size(1); i++) {
            Vector<Double> booleanVector = new Vector<>();
            for (int j = 0; j < discripters.size(0); j++) {
                booleanVector.add(discripters.get(j, i)[0]);
            }
            double hashNum = booleanVector.hashCode();
            doubleVector.add(hashNum);
        }
        return doubleVector;
    }

}
