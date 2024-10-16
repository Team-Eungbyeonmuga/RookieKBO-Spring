package com.Eungbyeonmuga.RookieKBO.domain.fastAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FastAPIRequest {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class GetGames {
        private Integer year;
        private Integer month;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class GetGameSummariesOnCalendar {
        private Integer year;
        private Integer month;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class GetGameDetailByDate {
        private Integer year;
        private Integer month;
        private Integer day;
    }
}
