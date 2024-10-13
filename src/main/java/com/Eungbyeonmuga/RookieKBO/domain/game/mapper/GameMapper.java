package com.Eungbyeonmuga.RookieKBO.domain.game.mapper;

import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.dto.FastAPIResponse;
import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameMapper {

    // FastAPIResponse.GetMatchesResponse를 GameResponse.GetMatchesResponse로 변환하는 메서드
    public GameResponse.GetMatches toGetMatches(FastAPIResponse.GetMatches fastAPIResponse) {
        List<GameResponse.MatchInfo> gameMatchInfos = fastAPIResponse.getMatchInfos().stream()
                .map(this::toMatchInfo)
                .collect(Collectors.toList());

        return GameResponse.GetMatches.builder()
                .matchInfos(gameMatchInfos)  // matchInfos 리스트가 null이 아닌지 확인
                .build();
    }

    // FastAPIResponse.MatchInfo를 GameResponse.MatchInfo로 변환하는 메서드
    private GameResponse.MatchInfo toMatchInfo(FastAPIResponse.MatchInfo fastAPIMatchInfo) {
        return GameResponse.MatchInfo.builder()
                .day(fastAPIMatchInfo.getDay())
                .time(fastAPIMatchInfo.getTime())
                .awayTeam(fastAPIMatchInfo.getAwayTeam())
                .homeTeam(fastAPIMatchInfo.getHomeTeam())
                .awayScore(fastAPIMatchInfo.getAwayScore())
                .homeScore(fastAPIMatchInfo.getHomeScore())
                .place(fastAPIMatchInfo.getPlace())
                .note(fastAPIMatchInfo.getNote())
                .build();
    }
}
