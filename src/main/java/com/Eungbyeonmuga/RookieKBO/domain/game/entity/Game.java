package com.Eungbyeonmuga.RookieKBO.domain.game.entity;

import com.Eungbyeonmuga.RookieKBO.domain.score.entity.Score;
import com.Eungbyeonmuga.RookieKBO.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
public class Game extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Score> homeScores = new ArrayList<>();

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Score> AwayScores = new ArrayList<>();

    private Integer homeScore;

    private Integer awayScore;

    private Inning inning;

    @Column(nullable = false)
    private Season season;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private String place;

    private String note;
}
