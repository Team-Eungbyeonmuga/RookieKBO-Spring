package com.Eungbyeonmuga.RookieKBO.domain.game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GameRequest {
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
    public static class GetGamesByDate {
        private String date;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class UpdateGamesDetailByDate {
        private Integer year;
        private Integer month;
        private Integer day;
    }
}
