package com.bbongdoo.doo.base.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Locale;

@Getter
@RequiredArgsConstructor
public enum StoreType {
    HYPER("hyper"),
    DS("ds"),
    AURORA("aurora"),
    EXP("exp");
    private String value;

    StoreType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * String code => enum code 형식으로 반환
     *
     * @param codeName
     * @return DeviceCode
     */
    public static StoreType findCode(String codeName) {
        return Arrays.stream(StoreType.values())
            .filter(c -> c.name().equals(codeName.toUpperCase(Locale.KOREA)))
            .findAny()
            .orElse(StoreType.HYPER);
    }

    /**
     * Enum code 유효성 검사
     *
     * @param codeName
     * @return boolean
     */
    public static boolean valid(String codeName) {
        return Arrays.stream(StoreType.values())
            .anyMatch(c -> c.name().equals(codeName.toUpperCase(Locale.KOREA)));
    }

    public static String getData(String codeName) {
        return Arrays.stream(StoreType.values())
            .filter(c -> c.name().equals(codeName.toUpperCase(Locale.KOREA)))
            .findAny()
            .map(c->c.getValue()).get();
    }
}
