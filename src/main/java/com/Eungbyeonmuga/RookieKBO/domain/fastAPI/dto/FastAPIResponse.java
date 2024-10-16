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


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class GetGameDetailByDate {
        @JsonProperty("gameDetails")
        private List<GameDetail> gameDetails;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class GameDetail {
        @JsonProperty("startDateTime")
        private String startDateTime;

        @JsonProperty("awayTeam")
        private String awayTeam;

        @JsonProperty("homeTeam")
        private String homeTeam;

        @JsonProperty("gameStatus")
        private String gameStatus;

        @JsonProperty("awayScores")
        private List<Integer> awayScores;

        @JsonProperty("homeScores")
        private List<Integer> homeScores;

        @JsonProperty("awayRHEB")
        private List<Integer> awayRHEB;

        @JsonProperty("homeRHEB")
        private List<Integer> homeRHEB;

        @JsonProperty("season")
        private String season;

        @JsonProperty("place")
        private String place;
    }


}
