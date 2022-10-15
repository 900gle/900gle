package com.bbongdoo.doo.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
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

    @Column(length = 1, nullable = false, name = "use_yn")
    private String useYn;

    @Builder
    public Keywords(
            String keyword,
            String use
    ) {
        this.keyword = keyword;
        this.useYn = use;
    }
}
