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
    public static class GetGames {
        @JsonProperty("gameSummariesInRegularSeason")
        private List<GameSummary> gameSummariesInRegularSeason;

        @JsonProperty("gameSummariesInPostSeason")
        private List<GameSummary> gameSummariesInPostSeason;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class GameSummary {
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

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class GetGameSummariesOnCalendar {
        @JsonProperty("gameSummariesOnCalendarInRegularSeason")
        private List<GameSummaryOnCalendar> gameSummariesOnCalendarInRegularSeason;

        @JsonProperty("gameSummariesOnCalendarInPostSeason")
        private List<GameSummaryOnCalendar> gameSummariesOnCalendarInPostSeason;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class GameSummaryOnCalendar {
        @JsonProperty("date")
        private String date;

        @JsonProperty("awayTeam")
        private String awayTeam;

        @JsonProperty("homeTeam")
        private String homeTeam;

        @JsonProperty("awayScore")
        private String awayScore;

        @JsonProperty("homeScore")
        private String homeScore;

        @JsonProperty("gameStatus")
        private String gameStatus;

        @JsonProperty("season")
        private String season;
    }


}
