package com.bbongdoo.doo.base.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IndexAlias{

    HYPER_ITEM("hyper-item"),
    DS_ITEM("ds-item"),
    AURORA_ITEM("aurora-item"),
    EXP_ITEM("exp-item"),

    HOME_RELATED("home-related"),
    HOME_LEAFLET("home-leaflet"),
    HOME_BANNER("home-banner"),
    HOME_AURORA("home-aurora"),
    HOME_ONEDAY("home-oneday"),
    KEYWORD_ATTRIBUTE("keyword-attribute");

    private final String alias;
}
