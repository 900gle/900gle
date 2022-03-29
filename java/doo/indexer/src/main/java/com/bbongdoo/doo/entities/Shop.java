package com.bbongdoo.doo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor(staticName = "create")
public class Shop {

    private String title;
    private String brand;
    private String price;
    private String category;
    private String image;

    private String type;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

}
