package com.doo.aqqle.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(
        name = "dictionary",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"word", "use_yn"}
                )
        }
)
public class Dictionarys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false, unique = true)
    private String word;

    @Column(length = 20, nullable = false, unique = true)
    private String type;

    @Column(length = 1, nullable = false, name = "use_yn")
    private String useYn;

    @Builder
    public Dictionarys(
            String word,
            String type,
            String use
    ) {
        this.word = word;
        this.type = type;
        this.useYn = use;
    }
}
