package com.Eungbyeonmuga.RookieKBO.domain.game.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    PREPARING("경기 예정"),
    DURING("경기 중"),
    OVER("경기 종료"),
    CANCEL("경기 취소");
    private final String toKorean;

    // 입력받은 한국어 값과 일치하는 Season을 반환하는 메서드
    public static Status fromKorean(String koreanName) {
        for (Status status : values()) {
            if (status.getToKorean().equals(koreanName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("해당 시즌 이름에 해당하는 Status 열거형이 없습니다: " + koreanName);
    }
}
