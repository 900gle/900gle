package com.bbongdoo.doo.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class TextEmbeddingDTO {
    private String tensorApiUrl;
    private String keyword;
}
