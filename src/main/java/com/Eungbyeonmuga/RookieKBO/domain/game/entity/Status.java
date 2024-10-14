package com.Eungbyeonmuga.RookieKBO.domain.game.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    PREPARING("경기 준비"),
    DURING("경기 중"),
    OVER("경기 종료"),
    CANCEL("취소");
    private final String toKorean;
}
