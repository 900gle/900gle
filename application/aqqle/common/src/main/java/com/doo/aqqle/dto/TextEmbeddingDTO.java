package com.doo.aqqle.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TextEmbeddingDTO {
    private String tensorApiUrl;
    private String keyword;
}
