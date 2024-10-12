package com.Eungbyeonmuga.RookieKBO.domain.game.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Inning {
    TOP_1ST("1회 초"),
    BOTTOM_1ST("1회 말"),
    TOP_2ND("2회 초"),
    BOTTOM_2ND("2회 말"),
    TOP_3TH("3회 초"),
    BOTTOM_3TH("3회 말"),
    TOP_4TH("4회 초"),
    BOTTOM_4TH("4회 말"),
    TOP_5TH("5회 초"),
    BOTTOM_5TH("5회 말"),
    TOP_6TH("6회 초"),
    BOTTOM_6TH("6회 말"),
    TOP_7TH("7회 초"),
    BOTTOM_7H("7회 말"),
    TOP_8H("8회 초"),
    BOTTOM_8TH("8회 말"),
    TOP_9TH("9회 초"),
    BOTTOM_9TH("9회 말"),
    TOP_10TH("10회 초"),
    BOTTOM_10TH("10회 말"),
    TOP_11TH("11회 초"),
    BOTTOM_11TH("11회 말"),
    TOP_12TH("12회 초"),
    BOTTOM_12TH("12회 말"),
    TOP_13TH("13회 초"),
    BOTTOM_13TH("13회 말"),
    TOP_14TH("14회 초"),
    BOTTOM_14TH("14회 말"),
    TOP_15TH("15회 초"),
    BOTTOM_15TH("15회 말");
    private final String toKorean;
}
