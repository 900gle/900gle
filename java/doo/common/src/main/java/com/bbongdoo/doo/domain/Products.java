package com.bbongdoo.doo.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Products {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 500, nullable = false)
    private String keyword;

    @Column(length = 500, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private long price;

    @Column(length = 500)
    private String brand;

    @Column(length = 2000, nullable = true)
    private String category;

    @Column(length = 200, nullable = true)
    private String category1;

    @Column(length = 200, nullable = true)
    private String category2;

    @Column(length = 200, nullable = true)
    private String category3;

    @Column(length = 200, nullable = true)
    private String category4;

    @Column(length = 200, nullable = true)
    private String category5;

    @Column(length = 200, nullable = true)
    private String image;

    @Column(length = 5000, nullable = true)
    private String imageVector;

    @Column(length = 50, nullable = true)
    private String type;

    @Column(length = 200, nullable = true)
    private LocalDateTime createdTime;

    @Column(length = 200, nullable = true)
    private LocalDateTime updatedTime;

    @Builder

    public Products(String keyword,
                    String name, long price,
                    String brand, String category,
                    String category1, String category2, String category3, String category4, String category5, String image, String imageVector, String type,
                    LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.keyword = keyword;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.category = category;
        this.category1 = category1;
        this.category2 = category2;
        this.category3 = category3;
        this.category4 = category4;
        this.category5 = category5;
        this.image = image;
        this.imageVector = imageVector;
        this.type = type;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

}
