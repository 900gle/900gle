package com.bbongdoo.doo.base.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShipMethod {
    TD_DRCT("점포 자차"),
    TD_DLV("점포 택배"),
    TD_PICK("점포 방문수령"),
    TD_POST("점포 우편"),
    TD_QUICK("점포 퀵"),
    DS_DRCT("셀러 자차"),
    DS_DLV("셀러 택배"),
    DS_PICK("셀러 방문수령"),
    DS_POST("셀러 우편"),
    DS_QUICK("셀러 퀵");

    private final String value;
}
