package com.Eungbyeonmuga.RookieKBO.domain.game.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class GameResponse {
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class GetGamesByYearAndMonth {
        @JsonProperty("gameInfos")
        private List<GameSummary> gameSummaries;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class GameSummary {
        @JsonProperty("startDateTime")
        private String startDateTime;

        @JsonProperty("awayTeam")
        private String awayTeam;

        @JsonProperty("homeTeam")
        private String homeTeam;

        @JsonProperty("awayScore")
        private String awayScore;

        @JsonProperty("homeScore")
        private String homeScore;

        @JsonProperty("place")
        private String place;

        @JsonProperty("note")
        private String note;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class GetGameDetail {
        private String season;
        private LocalDateTime startDateTime;
        private String place;
        private String gameStatus;
        private String homeTeam;
        private String awayTeam;
        private List<String> homeScores;
        private List<String> awayScores;
        private List<String> homeRHEB;
        private List<String> awayRHEB;
        private String homeScore;
        private String awayScore;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class GetGamesByDate {
        private List<GameInfo> gameInfos;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class GameInfo {
        private String season;
        private LocalDateTime startDateTime;
        private String place;
        private String gameStatus;
        private String homeTeam;
        private String awayTeam;
        private List<Integer> homeScores;
        private List<Integer> awayScores;
        private List<Integer> homeRHEB;
        private List<Integer> awayRHEB;
        private Integer homeScore;
        private Integer awayScore;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class UpdateGamesDetailByDate {
        private String test;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class GameDetails {
        private String startDateTime;
        private String awayTeam;
        private String homeTeam;
        private String gameStatus;
        private List<Integer> homeScores;
        private List<Integer> awayScores;
        private List<Integer> homeRHEB;
        private List<Integer> awayRHEB;
        private String season;
        private String place;
    }
}
