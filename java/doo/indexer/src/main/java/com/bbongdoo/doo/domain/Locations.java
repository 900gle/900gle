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
public class Locations {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 100, nullable = true)
    private String ip1;

    @Column(length = 100, nullable = true)
    private String ip2;

    @Column(length = 5, nullable = true)
    private String country;

    @Column(length = 100)
    private String city;

    @Column(length = 2000, nullable = true)
    private String town;

    @Column(length = 200, nullable = true)
    private Double lat;

    @Column(length = 200, nullable = true)
    private Double lon;

    @Column(length = 200, nullable = true)
    private String zone;

    @Column(length = 200, nullable = true)
    private LocalDateTime createdTime;

    @Column(length = 200, nullable = true)
    private LocalDateTime updatedTime;

    @Builder

    public Locations(String ip1, String ip2, String country, String city, String town, Double lat, Double lon, String zone, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.ip1 = ip1;
        this.ip2 = ip2;
        this.country = country;
        this.city = city;
        this.town = town;
        this.lat = lat;
        this.lon = lon;
        this.zone = zone;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
