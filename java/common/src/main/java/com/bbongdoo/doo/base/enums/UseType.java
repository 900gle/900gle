package com.bbongdoo.doo.base.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UseType {

    Y(true),
    N(false);

    private final boolean booleanValue;
}
