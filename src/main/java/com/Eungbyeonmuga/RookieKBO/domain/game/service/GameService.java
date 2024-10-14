package com.Eungbyeonmuga.RookieKBO.domain.game.service;

import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameResponse;

public interface GameService {
    GameResponse.GetMatchesByYearAndMonth getMatchesByYearAndMonth(Integer year, Integer month);

    // TODO: 코드 리팩토링 필요
    // 우천 취소와 같은 기타 상황을 위한 기본 Game 셋팅
    GameResponse.GetMatchesByYearAndMonth getMatchSummariesOnCalendar(Integer year, Integer month);
}
