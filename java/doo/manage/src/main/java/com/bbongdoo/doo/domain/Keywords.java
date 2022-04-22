package com.bbongdoo.doo.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(
        name = "keywords",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"keyword", "use_yn"}
                )
        }
)
public class Keywords {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 200, nullable = false, unique = true)
    private String keyword;

    @Column(length = 50, nullable = true)
    private String use_yn;

    @Builder
    public Keywords(
            String keyword,
            String use
    ) {
        this.keyword = keyword;
        this.use_yn = use;
    }
}
