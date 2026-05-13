package com.doo.aqqle.repository;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Entity
@Table(name = "stock_data_partition")
public class StockData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String company;

    @Column(length = 20)
    private String companyCode;

    @Column(length = 30)
    private String tradingDate;

    @Column
    private float open;

    @Column
    private float high;

    @Column
    private float low;

    @Column
    private float close;

    @Column
    private float adjClose;

    @Column
    private long volume;

    @Builder
    public StockData(

            String company,
            String companyCode,
            String tradingDate,
            float open,
            float high,
            float low,
            float close,
            float adjClose,
            long volume
    ) {
        this.company = company;
        this.companyCode = companyCode;
        this.tradingDate = tradingDate;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjClose = adjClose;
        this.volume = volume;
    }
}
