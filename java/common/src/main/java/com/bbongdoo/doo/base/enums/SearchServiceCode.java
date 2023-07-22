package com.bbongdoo.doo.base.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Locale;

@Getter
@RequiredArgsConstructor
public enum SearchServiceCode {
    HOME, AURORA, EXP, SELLER, HMS;

    public String toLowCase(){
        return this.name().toLowerCase();
    }

    /**
     * String code => enum code 형식으로 반환
     *
     * @param codeName
     * @return DeviceCode
     */
    public static SearchServiceCode findCode(String codeName) {
        return Arrays.stream(SearchServiceCode.values())
            .filter(c -> c.name().equals(codeName.toUpperCase(Locale.KOREA)))
            .findAny()
            .orElse(SearchServiceCode.HOME);
    }

    /**
     * Enum code 유효성 검사
     *
     * @param codeName
     * @return boolean
     */
    public static boolean valid(String codeName) {
        return Arrays.stream(SearchServiceCode.values())
            .anyMatch(c -> c.name().equals(codeName.toUpperCase(Locale.KOREA)));
    }
}
