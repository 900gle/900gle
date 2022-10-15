package com.bbongdoo.doo.service;


import com.bbongdoo.doo.component.ImageToVectorOpenCV;
import com.bbongdoo.doo.dto.VectorDTO;
import com.bbongdoo.doo.model.response.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Vector;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpencvImageIndexService {

    private final ResponseService responseService;
    private final RestHighLevelClient client;

    @Value("${file.dir.temp}")
    private String filePath;


    public CommonResult staticIndex(VectorDTO vectorDTO) {


        try {
            Vector<Double> doubleVector = ImageToVectorOpenCV.getVector(VectorDTO.builder().dirPath(filePath).file(vectorDTO.getFile()).build());


            doubleVector.stream().forEach(x-> System.out.println(x));

        } catch (IOException e) {
            e.printStackTrace();
        }


        return responseService.getSuccessResult();
    }



}
