package com.Eungbyeonmuga.RookieKBO.domain.game.mapper;

import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Game;
import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Season;
import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Status;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class GameMapper {


//    public GameResponse.GetMatches toGetMatches(List<Game> games) {
//        List<GameResponse.MatchSummary> gameMatchSummaries = games.stream()
//                .map(this::toMatchInfo)
//                .collect(Collectors.toList());
//
//        return GameResponse.GetMatches.builder()
//                .matchSummaries(gameMatchSummaries)  // matchInfos 리스트가 null이 아닌지 확인
//                .build();
//    }

    public Game toNewGame(Season season, LocalDateTime startDateTime, String place, String homeScore, String awayScore, Status status, String note) {
        return Game.builder()
                .startDateTime(startDateTime)
                .season(season)
                .homeScores(new ArrayList<>())
                .AwayScores(new ArrayList<>())
                .homeScore(safeParseInt(homeScore))
                .awayScore(safeParseInt(awayScore))
                .place(place)
                .status(status)
                .note(note)
                .build();
    }

//    public GameResponse.MatchSummary toMatchInfo(Game game, Team homeTeam, Team awayTeam) {
//        return GameResponse.MatchSummary.builder()
//                .startDateTime(game.getStartDateTime().toString())
//                .awayTeam(awayTeam.getName())
//                .homeTeam(homeTeam.getName())
//                .awayScore(fastAPIMatchInfo.getAwayScore())
//                .homeScore(fastAPIMatchInfo.getHomeScore())
//                .place(fastAPIMatchInfo.getPlace())
//                .note(fastAPIMatchInfo.getNote())
//                .build();
//    }

    public Integer safeParseInt(String value) {
        if (value == null || value.isEmpty()) {
            return null;  // 혹은 기본값을 반환하거나 적절한 처리를 해줘
        }
        return Integer.parseInt(value);
    }
}
