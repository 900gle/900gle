package com.bbongdoo.doo.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(
        name = "users"
)
public class Users {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 200, nullable = false)
    private String name;

    @Column(length = 1, nullable = false, name = "use_yn")
    private String useYn;

    @Builder
    public Users(
            String name,
            String use
    ) {
        this.name = name;
        this.useYn = use;
    }
}
