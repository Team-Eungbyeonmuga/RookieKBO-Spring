package com.Eungbyeonmuga.RookieKBO.domain.game.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Season {
    REGULAR_SEASON("정규시즌"),
    POST_SEASON("포스트시즌");
    private final String toKorean;

    // 입력받은 한국어 값과 일치하는 Season을 반환하는 메서드
    public static Season fromKorean(String koreanName) {
        for (Season season : values()) {
            if (season.getToKorean().equals(koreanName)) {
                return season;
            }
        }
        throw new IllegalArgumentException("해당 시즌 이름에 해당하는 Season 열거형이 없습니다: " + koreanName);
    }
}
