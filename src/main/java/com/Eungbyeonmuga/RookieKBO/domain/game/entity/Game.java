package com.Eungbyeonmuga.RookieKBO.domain.game.entity;

import com.Eungbyeonmuga.RookieKBO.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.cglib.core.Local;

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

    @Column(nullable = false)
    private Season season;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

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
    private String place;

    private String note;

    public void updateSeason(Season season) {
        this.season = season;
    }

    public void updateStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void updateHomeScores(List<Integer> homeScores) {
        this.homeScores = homeScores;
    }

    public void updateAwayScores(List<Integer> awayScores) {
        this.awayScores = awayScores;
    }

    public void updateHomeRHEB(List<Integer> homeRHEB) {
        this.homeRHEB = homeRHEB;
    }

    public void updateAwayRHEB(List<Integer> awayRHEB) {
        this.awayRHEB = awayRHEB;
    }

    public void updatePlace(String place) {
        this.place = place;
    }

    public void updateNote(String note) {
        this.note = note;
    }
}
