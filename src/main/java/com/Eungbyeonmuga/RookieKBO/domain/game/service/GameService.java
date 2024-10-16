package com.Eungbyeonmuga.RookieKBO.domain.game.service;

import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameResponse;

import java.util.List;

public interface GameService {
    GameResponse.GetGamesByYearAndMonth createGameBaseInfoForMonthFromKBOList(Integer year, Integer month);

    // TODO: 코드 리팩토링 필요
    // 우천 취소와 같은 기타 상황을 위한 기본 Game 셋팅
    GameResponse.GetGamesByYearAndMonth createGameBaseInfoForMonthFromKBOCalendar(Integer year, Integer month);

    List<Integer> getScoresTest();

    // 우천 취소와 같은 기타 상황을 위한 기본 Game 셋팅
    GameResponse.GetGamesByDate getGamesByDate(String date);

    GameResponse.UpdateGamesDetailByDate updateGamesDetailByDate(Integer year, Integer month, Integer day);
}
