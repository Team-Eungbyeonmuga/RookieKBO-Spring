package com.Eungbyeonmuga.RookieKBO.domain.fastAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class FastAPIResponse {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class GetMatches {
        @JsonProperty("matchSummariesInRegularSeason")
        private List<MatchSummary> matchSummariesInRegularSeason;

        @JsonProperty("matchSummariesInPostSeason")
        private List<MatchSummary> matchSummariesInPostSeason;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class MatchSummary {
        @JsonProperty("date")
        private String date;

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
