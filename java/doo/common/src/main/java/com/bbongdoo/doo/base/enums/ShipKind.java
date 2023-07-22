package com.bbongdoo.doo.base.enums;

public enum ShipKind {
    FREE("무료배송"),
    FIXED("유료배송"),
    COND("조건부무료배송");
    private String value;

    ShipKind(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
