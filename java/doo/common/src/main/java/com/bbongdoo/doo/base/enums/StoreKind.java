package com.bbongdoo.doo.base.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Locale;

@Getter
@RequiredArgsConstructor
public enum StoreKind {
    NOR("일반점"),
    DLV("택배점");

    private final String value;

    /**
     * String code => enum code 형식으로 반환
     *
     * @param codeName
     * @return DeviceCode
     */
    public static StoreKind findCode(String codeName) {
        return Arrays.stream(StoreKind.values())
            .filter(c -> c.name().equals(codeName.toUpperCase(Locale.KOREA)))
            .findAny()
            .orElse(StoreKind.NOR);
    }

    /**
     * Enum code 유효성 검사
     *
     * @param codeName
     * @return boolean
     */
    public static boolean valid(String codeName) {
        return Arrays.stream(com.bbongdoo.doo.base.enums.SiteType.values())
            .anyMatch(c -> c.name().equals(codeName.toUpperCase(Locale.KOREA)));
    }
}
