package com.Eungbyeonmuga.RookieKBO.domain.game.mapper;

import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameResponse;
import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Game;
import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Season;
import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Status;
import com.Eungbyeonmuga.RookieKBO.domain.team.entity.Team;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
                .season(season)
                .startDateTime(startDateTime)
                .homeScores(season == Season.REGULAR_SEASON
                        ? initializeScores(12)   // 정규시즌이면 12개의 -1
                        : initializeScores(15))  // 포스트시즌이면 15개의 -1
                .awayScores(season == Season.REGULAR_SEASON
                        ? initializeScores(12)   // 정규시즌이면 12개의 -1
                        : initializeScores(15))  // 포스트시즌이면 15개의 -1
                .homeScores(new ArrayList<>())
                .awayScores(new ArrayList<>())
                .homeRHEB(new ArrayList<>(Arrays.asList(-1, -1, -1, -1)))
                .awayRHEB(new ArrayList<>(Arrays.asList(-1, -1, -1, -1)))
                .place(place)
                .status(status)
                .note(note)
                .build();
    }

    public List<Integer> initializeScores(int count) {
        return new ArrayList<>(Collections.nCopies(count, -1));
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



    public GameResponse.GetGamesByDate toGetGamesByDate(List<GameResponse.GameInfo> gameInfos) {
        return GameResponse.GetGamesByDate.builder()
                .gameInfos(gameInfos)
                .build();
    }


    public GameResponse.GameInfo toGameInfo(Game game, Team homeTeam, Team awayTeam) {
        return GameResponse.GameInfo.builder()
                .season(game.getSeason().getToKorean())
                .startDateTime(game.getStartDateTime())
                .place(game.getPlace())
                .gameStatus(game.getStatus().getToKorean())
                .homeTeam(homeTeam.getName())
                .awayTeam(awayTeam.getName())
                .homeScores(game.getHomeScores())
                .awayScores(game.getAwayScores())
                .homeRHEB(game.getHomeRHEB())
                .awayRHEB(game.getAwayRHEB())
                .homeScore(game.getHomeRHEB().get(0))
                .awayScore(game.getAwayRHEB().get(0))
                .build();
    }

    private Integer safeParseInt(String value) {
        if (value == null || value.equals("-") || value.isEmpty()) {
            return null;  // 혹은 기본값을 반환하거나 적절한 처리를 해줘
        }
        return Integer.parseInt(value);
    }

    // 안전하게 리스트의 첫 번째 요소를 가져오는 헬퍼 메소드
    private Integer getFirstElementOrDefault(List<Integer> list) {
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return 0;  // 기본값으로 0을 반환하거나, 필요에 따라 다른 기본값 설정
    }
}
