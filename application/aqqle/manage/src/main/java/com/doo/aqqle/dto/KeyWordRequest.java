package com.doo.aqqle.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KeyWordRequest {
    private List<String> words;
    private String type;
}
