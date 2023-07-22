package com.bbongdoo.doo.base.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Locale;

@Getter
@RequiredArgsConstructor
public enum SiteType {
    UNKNOWN, HOME, HMS;

    public String toLowCase(){
        return this.name().toLowerCase();
    }

    /**
     * String code => enum code 형식으로 반환
     *
     * @param codeName
     * @return DeviceCode
     */
    public static SiteType findCode(String codeName) {
        return Arrays.stream(SiteType.values())
            .filter(c -> c.name().equals(codeName.toUpperCase(Locale.KOREA)))
            .findAny()
            .orElse(SiteType.UNKNOWN);
    }

    /**
     * Enum code 유효성 검사
     *
     * @param codeName
     * @return boolean
     */
    public static boolean valid(String codeName) {
        return Arrays.stream(SiteType.values())
            .anyMatch(c -> c.name().equals(codeName.toUpperCase(Locale.KOREA)));
    }
}
