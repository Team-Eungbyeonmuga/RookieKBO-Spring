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
    public static class GetMatches {
        private Integer year;
        private Integer month;
        private String season;
    }
}
