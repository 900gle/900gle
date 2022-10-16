package com.bbongdoo.doo.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class ImageSearchDTO {
    private int imageId;
    private String filePath;
    private String tensorApi;
    private MultipartFile file;
    private String dirPath;



}
