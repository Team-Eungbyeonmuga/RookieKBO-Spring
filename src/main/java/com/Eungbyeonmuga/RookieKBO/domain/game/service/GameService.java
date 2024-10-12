package com.Eungbyeonmuga.RookieKBO.domain.game.service;

import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.dto.FastAPIResponse;
import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameResponse;

import java.util.List;

public interface GameService {
    // TODO: Response FastAPI -> GameResponse로 변경하기
    List<FastAPIResponse.MatchInfo> getMatches(Integer year, Integer month, String matchType);
}
