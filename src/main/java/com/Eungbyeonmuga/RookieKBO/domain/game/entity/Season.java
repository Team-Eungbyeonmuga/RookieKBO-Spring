package com.Eungbyeonmuga.RookieKBO.domain.game.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Season {
    REGULAR_SEASON("정규 시즌"),
    POST_SEASON("포스트 시즌");
    private final String toKorean;
}
