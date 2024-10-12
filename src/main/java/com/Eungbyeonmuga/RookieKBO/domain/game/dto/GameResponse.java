package com.Eungbyeonmuga.RookieKBO.domain.game.dto;

import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.dto.FastAPIResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class GameResponse {
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class GetMatchesResponse {
        private List<FastAPIResponse.MatchInfo> matchInfos;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
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
