package com.Eungbyeonmuga.RookieKBO.domain.game.dto;

import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.dto.FastAPIResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class GameResponse {
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class GetMatches {
        @JsonProperty("matchInfos")
        private List<MatchInfo> matchInfos;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class MatchInfo {
        @JsonProperty("day")
        private String day;

        @JsonProperty("time")
        private String time;

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
}
