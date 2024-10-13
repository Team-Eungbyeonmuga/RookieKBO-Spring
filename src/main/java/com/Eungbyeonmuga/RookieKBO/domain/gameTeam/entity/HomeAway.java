package com.Eungbyeonmuga.RookieKBO.domain.gameTeam.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HomeAway {
    HOME("홈"),
    AWAY("어웨이");
    private final String toKorean;
}
