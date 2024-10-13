package com.Eungbyeonmuga.RookieKBO.domain.game.service;

import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameResponse;

public interface GameService {
    GameResponse.GetMatches getMatchesByYearAndMonthAndSeason(Integer year, Integer month, String season);
}
