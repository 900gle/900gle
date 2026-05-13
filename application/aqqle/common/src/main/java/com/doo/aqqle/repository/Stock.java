package com.doo.aqqle.repository;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "stock",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"companyCode"}
                )
        })
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String company;

    @Column(length = 20, nullable = false, unique = true)
    private String companyCode;

    @Column(length = 30, nullable = false, unique = true)
    private String exchange;

    @Column(length = 30, nullable = false, unique = true)
    private String startDate;

    @Column(length = 30, nullable = false, unique = true)
    private String endDate;

    @Column(length = 20, nullable = false, unique = true)
    private String type;

    @Column(length = 1, nullable = false, name = "use_yn")
    private String useYn;

    @Builder
    public Stock(
            String company,
            String companyCode,
            String exchange,
            String startDate,
            String endDate,
            String type,
            String use
    ) {
        this.company = company;
        this.companyCode = companyCode;
        this.exchange = exchange;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.useYn = use;
    }
}
