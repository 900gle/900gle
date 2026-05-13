package com.doo.aqqle.repository.member;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "members")
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false, unique = true)
    private String userId;

    @Column(length = 200, nullable = false, unique = true)
    private String name;

    @Column(length = 1, nullable = false, name = "use_yn")
    private String useYn;

    @Builder
    public Members(
            String userId,
            String name,
            String use
    ) {
        this.userId = userId;
        this.name = name;
        this.useYn = use;
    }
}
