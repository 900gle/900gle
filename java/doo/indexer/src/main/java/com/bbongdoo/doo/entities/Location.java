package com.bbongdoo.doo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor(staticName = "create")
public class Location {

    private String id;
    private String ip1;
    private String ip2;
    private String country;
    private String city;
    private String town;
    private String lat;
    private String lon;
    private String zone;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

}
