package com.Eungbyeonmuga.RookieKBO.domain.game.mapper;

import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.dto.FastAPIResponse;
import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameResponse;
import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Game;
import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Inning;
import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Season;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

//    public GameResponse.GetMatches toGetMatches(List<Game> games) {
//        List<GameResponse.MatchInfo> gameMatchInfos = fastAPIResponse.getMatchInfos().stream()
//                .map(this::toMatchInfo)
//                .collect(Collectors.toList());
//
//        return GameResponse.GetMatches.builder()
//                .matchInfos(gameMatchInfos)  // matchInfos 리스트가 null이 아닌지 확인
//                .build();
//    }

    // FastAPIResponse.MatchInfo를 GameResponse.MatchInfo로 변환하는 메서드
    public GameResponse.MatchInfo toMatchInfo(FastAPIResponse.MatchInfo fastAPIMatchInfo) {
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

    public Game toGame(Season season, LocalDateTime startDateTime, String place) {
        return Game.builder()
                .startDateTime(startDateTime)
                .season(season)
                .homeScores(new ArrayList<>())
                .AwayScores(new ArrayList<>())
                .place(place)
                .build();
    }

//    public GameResponse.MatchInfo toMatchInfo(Game game) {
//        return GameResponse.MatchInfo.builder()
////                .day(game.)
////                .time(fastAPIMatchInfo.getTime())
//                .awayTeam(game.)
//                .homeTeam(fastAPIMatchInfo.getHomeTeam())
//                .awayScore(fastAPIMatchInfo.getAwayScore())
//                .homeScore(fastAPIMatchInfo.getHomeScore())
//                .place(fastAPIMatchInfo.getPlace())
//                .note(fastAPIMatchInfo.getNote())
//                .build();
//    }
}
