package com.Eungbyeonmuga.RookieKBO.domain.score.entity;

import com.Eungbyeonmuga.RookieKBO.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
public class Score extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id")
    private Long id;

    @Column(nullable = false)
    private Long score;

    @Column(nullable = false)
    private ScoreType scoreType;
}
