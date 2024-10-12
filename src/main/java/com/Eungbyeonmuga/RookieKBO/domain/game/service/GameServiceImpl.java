package com.Eungbyeonmuga.RookieKBO.domain.game.service;

import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.client.FastAPIClient;
import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.dto.FastAPIRequest;
import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.dto.FastAPIResponse;
import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameRequest;
import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GameServiceImpl implements GameService {

    private final FastAPIClient fastAPIClient;

    // TODO: Response FastAPI -> GameResponse로 변경하기
    @Override
    public List<FastAPIResponse.MatchInfo> getMatches(Integer year, Integer month, String matchType) {
        FastAPIRequest.GetMatches getMatchesRequest = FastAPIRequest.GetMatches.builder()
                .year(year)
                .month(month)
                .matchType(matchType)
                .build();
        return fastAPIClient.getMatches(getMatchesRequest);
    }
}
