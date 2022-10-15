package com.bbongdoo.doo.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class VectorDTO {
    private String tensorApiUrl;
    private String dirPath;
    private MultipartFile file;
    private String type;
}
