package com.doo.aqqle.repository;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "investment",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"company", "exchange"}
                )
        })
public class Investments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false, unique = true)
    private String company;

    @Column(length = 200, nullable = false, unique = true)
    private String exchange;

    @Column(length = 20, nullable = false, unique = true)
    private String type;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long quantity;

    @Column(length = 1, nullable = false, name = "use_yn")
    private String useYn;

    @Builder
    public Investments(
            String company,
            String exchange,
            String type,
            Long price,
            Long quantity,
            String use
    ) {
        this.company = company;
        this.exchange = exchange;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.useYn = use;
    }
}
