package com.Eungbyeonmuga.RookieKBO.domain.game.service;

import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameResponse;

public interface GameService {
    GameResponse.GetMatchesByYearAndMonth getMatchesByYearAndMonth(Integer year, Integer month);
}
