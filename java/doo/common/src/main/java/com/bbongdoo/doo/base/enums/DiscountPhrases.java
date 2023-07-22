package com.bbongdoo.doo.base.enums;

public enum DiscountPhrases {
    CARD_DISCOUNT_PRICE("카드상품할인가"),
    SELLER_DISCOUNT_PRICE("셀러상품할인가"),
    ITEM_DISCOUNT_PRICE("상품할인가"),
    BASIC_DISCOUNT_PRICE("기본행사할인가"),
    BASIC_FIXED_PRICE("기본행사고정가"),
    DEFAULT_PRICE("판매가")
    ;
    private String discountValue;

    DiscountPhrases(String discountValue) {
        this.discountValue = discountValue;
    }

    public String getValue() {
        return discountValue;
    }
}
