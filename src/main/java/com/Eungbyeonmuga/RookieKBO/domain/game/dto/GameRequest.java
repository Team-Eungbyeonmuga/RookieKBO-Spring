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
    public static class GetMatches {
        private Integer year;
        private Integer month;
    }
}
