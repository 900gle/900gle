package com.doo.aqqle;


import lombok.Getter;

@Getter
public enum HostUrl {

    EMBEDDING("http://localhost:5000/vectors"),
    NAVER("https://search.shopping.naver.com/search/all");

    private String url;

    HostUrl(String url) {
        this.url = url;
    }
}
