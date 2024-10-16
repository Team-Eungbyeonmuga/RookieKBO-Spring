package com.Eungbyeonmuga.RookieKBO.domain.game.service;

import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameResponse;

import java.util.List;

public interface GameService {
    List<Integer> getScoresTest();

    // 우천 취소와 같은 기타 상황을 위한 기본 Game 셋팅
    GameResponse.GetGamesByDate getGamesByDate(String date);


}
