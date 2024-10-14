package com.Eungbyeonmuga.RookieKBO.domain.game.service;

import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.client.FastAPIClient;
import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.dto.FastAPIRequest;
import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.dto.FastAPIResponse;
import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameResponse;
import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Game;
import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Season;
import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Status;
import com.Eungbyeonmuga.RookieKBO.domain.game.mapper.GameMapper;
import com.Eungbyeonmuga.RookieKBO.domain.game.repository.GameRepository;
import com.Eungbyeonmuga.RookieKBO.domain.gameTeam.service.GameTeamService;
import com.Eungbyeonmuga.RookieKBO.domain.team.entity.Team;
import com.Eungbyeonmuga.RookieKBO.domain.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final FastAPIClient fastAPIClient;
    private final GameMapper gameMapper;
    private final GameTeamService gameTeamService;
    private final TeamService teamService;

    // 우천 취소와 같은 기타 상황을 위한 기본 Game 셋팅
    @Override
    public GameResponse.GetMatchesByYearAndMonth getMatchesByYearAndMonth(Integer year, Integer month) {

        List<Game> games = gameRepository.findByStartDate(convertToStartDateTime(year, month, 1, 0, 0));
        FastAPIRequest.GetMatches getMatchesRequest;

        // 이전에 크롤링되지 않아서 Spring DB에 없을 때
        if(games.isEmpty()) {
            getMatchesRequest = FastAPIRequest.GetMatches
                    .builder()
                    .year(year)
                    .month(month)
                    .build();
            // FastAPI 서버에서 클로링 후 Game 생성 및 GameTeam 생성
            FastAPIResponse.GetMatches matchSummaries = fastAPIClient.getMatches(getMatchesRequest);
            System.out.println(matchSummaries);
            // 정규 시즌 생성
            for(FastAPIResponse.MatchSummary matchSummary : matchSummaries.getMatchSummariesInRegularSeason()) {
                System.out.println(matchSummary);
                List<Integer> timeIntegerList = parseTimeStringToInteger(matchSummary.getDate(), matchSummary.getTime());
                LocalDateTime startDateTime = convertToStartDateTime(year, month, timeIntegerList.get(0), timeIntegerList.get(1), timeIntegerList.get(2));
                Status status;
                if (matchSummary.getNote().contains("취소")) {
                    status = Status.CANCEL;
                } else {
                    if (LocalDateTime.now().plusMinutes(5).isAfter(startDateTime)) {
                        status = Status.OVER;
                    } else {
                        status = Status.PREPARING;
                    }
                }
                Game newGame = gameMapper.toNewGame(Season.REGULAR_SEASON, startDateTime, matchSummary.getPlace(), matchSummary.getHomeScore(), matchSummary.getAwayScore(), status, matchSummary.getNote());
                gameRepository.save(newGame);
                Team homeTeam = teamService.findTeamByName(matchSummary.getHomeTeam());
                Team awayTeam = teamService.findTeamByName(matchSummary.getAwayTeam());

                gameTeamService.createGameTeam(newGame, homeTeam, awayTeam);
            }
            // 포스트 시즌 생성
            for(FastAPIResponse.MatchSummary matchSummary : matchSummaries.getMatchSummariesInPostSeason()) {
                System.out.println(matchSummary);
                List<Integer> timeIntegerList = parseTimeStringToInteger(matchSummary.getDate(), matchSummary.getTime());
                LocalDateTime startDateTime = convertToStartDateTime(year, month, timeIntegerList.get(0), timeIntegerList.get(1), timeIntegerList.get(2));
                Status status;
                if (matchSummary.getNote().contains("취소")) {
                    status = Status.CANCEL;
                } else {
                    if (LocalDateTime.now().plusMinutes(5).isAfter(startDateTime)) {
                        status = Status.OVER;
                    } else {
                        status = Status.PREPARING;
                    }
                }
                Game newGame = gameMapper.toNewGame(Season.POST_SEASON, startDateTime, matchSummary.getPlace(), matchSummary.getHomeScore(), matchSummary.getAwayScore(), status, matchSummary.getNote());
                gameRepository.save(newGame);
                Team homeTeam = teamService.findTeamByName(matchSummary.getHomeTeam());
                Team awayTeam = teamService.findTeamByName(matchSummary.getAwayTeam());

                gameTeamService.createGameTeam(newGame, homeTeam, awayTeam);
            }
//            return gameMapper.toGetMatches(fastAPIClient.getMatches(getMatchesRequest));
            return null;
        } else {
            return null;
        }
    }

//    @Override
//    public GameResponse.GetMatchDetail getMatchesByDate(Integer year, Integer month, Integer day) {
//
//        List<Game> games = gameRepository.findByStartDate(convertToStartDateTime(year, month, day, 0, 0));
//        FastAPIRequest.GetMatches getMatchesRequest;
//
//        // 이전에 크롤링되지 않아서 Spring DB에 없을 때
//        if(games.isEmpty()) {
//            getMatchesRequest = FastAPIRequest.GetMatches
//                    .builder()
//                    .year(year)
//                    .month(month)
////                    .season(season)
//                    .build();
//            // FastAPI 서버에서 클로링 후 Game 생성 및 GameTeam 생성
//            FastAPIResponse.GetMatches getMatches = fastAPIClient.getMatches(getMatchesRequest);
//            for(FastAPIResponse.MatchInfo matchInfo : getMatches.getMatchInfos()) {
//                List<Integer> timeIntegerList = parseTimeStringToInteger(matchInfo.getDate(), matchInfo.getTime());
//                LocalDateTime startDateTime = convertToStartDateTime(year, month, timeIntegerList.get(0), timeIntegerList.get(1), timeIntegerList.get(2));
//                Game newGame = gameMapper.toGame(Season.fromKorean(season), startDateTime, matchInfo.getPlace());
//                gameRepository.save(newGame);
//                Team homeTeam = teamService.findTeamByName(matchInfo.getHomeTeam());
//                Team awayTeam = teamService.findTeamByName(matchInfo.getAwayTeam());
//
//                gameTeamService.createGameTeam(newGame, homeTeam, awayTeam);
//            }
//            return gameMapper.toGetMatches(fastAPIClient.getMatches(getMatchesRequest));
//        } else {
//            return null;
//        }
//    }

    // String 형식의 Time 데이터를 Integer 변환
    public List<Integer> parseTimeStringToInteger(String date, String time) {
        System.out.println("TEST");
        System.out.println(date);
        System.out.println(time);
        System.out.println("TEST");
        String[] dateParts = date.split("\\(");    // "10.02(수)" -> ["10.02", "(수)"]
        String[] timeParts = time.split(":");
        String dayString = dateParts[0];  // "10.02"
        String[] dayMonthParts = dayString.split("\\.");  // "10.02" -> ["10", "02"]
        Integer day = Integer.parseInt(dayMonthParts[1]);  // "02" -> 2
        Integer hour = Integer.parseInt(timeParts[0]);
        Integer minute = Integer.parseInt(timeParts[1]);
        return Arrays.asList(day, hour, minute);
    }

    public LocalDateTime convertToStartDateTime(Integer year, Integer month, Integer day, Integer hour, Integer minute) {
        // day가 0이면 기본값으로 1을 설정
        day = (day == 0) ? 1 : day;

        // 해당 년, 월, 일, 시간, 분으로 LocalDateTime 생성
        return LocalDateTime.of(year, month, day, hour, minute);
    }
}
