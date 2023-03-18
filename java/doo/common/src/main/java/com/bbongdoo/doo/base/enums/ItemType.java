package com.bbongdoo.doo.base.enums;

public enum ItemType {
    N("새상품"),
    U("중고"),
    R("리퍼"),
    B("반품(리세일)"),
    C("주문제작"),
    T("렌탈"),
    M("휴대폰"),
    E("e-ticket(기프티콘)")
    ;
    private String value;

    ItemType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
