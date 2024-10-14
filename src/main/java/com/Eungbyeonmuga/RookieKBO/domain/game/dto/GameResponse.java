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
    public static class GetMatchesByYearAndMonth {
        @JsonProperty("matchInfos")
        private List<MatchSummary> matchSummaries;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class MatchSummary {
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
    public static class GetMatchDetail {
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


}
