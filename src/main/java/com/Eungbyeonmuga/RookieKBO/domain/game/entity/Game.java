package com.Eungbyeonmuga.RookieKBO.domain.game.entity;

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

    @Builder.Default
    @CollectionTable(name = "score", joinColumns = @JoinColumn(name = "game_id"))
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Integer> homeScores = new ArrayList<>();

    @Builder.Default
    @CollectionTable(name = "score", joinColumns = @JoinColumn(name = "game_id"))
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Integer> awayScores = new ArrayList<>();

    @Builder.Default
    @CollectionTable(name = "score", joinColumns = @JoinColumn(name = "game_id"))
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Integer> homeRHEB = new ArrayList<>();

    @Builder.Default
    @CollectionTable(name = "score", joinColumns = @JoinColumn(name = "game_id"))
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Integer> awayRHEB = new ArrayList<>();

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
