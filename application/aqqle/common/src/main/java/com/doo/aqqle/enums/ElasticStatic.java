package com.doo.aqqle.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ElasticStatic {

    SHOP("shop"),
    INVESTMENT("investment"),
    YAHOO("yahoo"),
    LOCATION("location");

    private final String alias;
}
