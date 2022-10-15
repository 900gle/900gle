package com.bbongdoo.doo.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class ImageIndexDTO {

    private int imageId;
    private String imageName;
    private String filePath;
    private String tensorApi;
    private MultipartFile file;




}
