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
import com.Eungbyeonmuga.RookieKBO.domain.gameTeam.entity.GameTeam;
import com.Eungbyeonmuga.RookieKBO.domain.gameTeam.entity.HomeAway;
import com.Eungbyeonmuga.RookieKBO.domain.gameTeam.service.GameTeamService;
import com.Eungbyeonmuga.RookieKBO.domain.team.entity.Team;
import com.Eungbyeonmuga.RookieKBO.domain.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    // TODO: 코드 리팩토링 필요
    // 우천 취소와 같은 기타 상황을 위한 기본 Game 셋팅

    /***
     * 사용 X KBO 리스트 기반인데, 데이터가 정확하지 않아서 캘린더 기반으로 변경
     * @param year
     * @param month
     */
    @Override
    public GameResponse.GetMatchesByYearAndMonth createGameBaseInfoForMonthFromKBOList(Integer year, Integer month) {

        FastAPIRequest.GetMatches getMatchesRequest = FastAPIRequest.GetMatches
                .builder()
                .year(year)
                .month(month)
                .build();

        // FastAPI 서버에서 클로링 후 Game 생성 및 GameTeam 생성
        FastAPIResponse.GetMatches matchSummaries = fastAPIClient.getMatches(getMatchesRequest);
        System.out.println(matchSummaries);
        // 정규 시즌 생성
        for (FastAPIResponse.MatchSummary matchSummary : matchSummaries.getMatchSummariesInRegularSeason()) {
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
        for (FastAPIResponse.MatchSummary matchSummary : matchSummaries.getMatchSummariesInPostSeason()) {
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

        return null;
    }

    // TODO: 코드 리팩토링 필요

    /***
     * KBO 홈페이지의 경기 일정 캘린더 기준 크롤링 데이터 패칭
     * @param year
     * @param month
     */
    // 우천 취소와 같은 기타 상황을 위한 기본 Game 셋팅
    @Override
    public GameResponse.GetMatchesByYearAndMonth createGameBaseInfoForMonthFromKBOCalendar(Integer year, Integer month) {

        List<Game> games = gameRepository.findByStartDate(convertToStartDateTime(year, month, 1, 0, 0));
        FastAPIRequest.GetMatchSummariesOnCalendar getMatchSummariesOnCalendar = FastAPIRequest.GetMatchSummariesOnCalendar
                .builder()
                .year(year)
                .month(month)
                .build();


        // FastAPI 서버에서 클로링 후 Game 생성 및 GameTeam 생성
        FastAPIResponse.GetMatchSummariesOnCalendar matchSummariesOnCalendar = fastAPIClient.getMatchSummariesOnCalendar(getMatchSummariesOnCalendar);
        System.out.println(matchSummariesOnCalendar);
        // 정규 시즌 생성
        for (FastAPIResponse.MatchSummaryOnCalendar matchSummaryOnCalendar : matchSummariesOnCalendar.getMatchSummariesOnCalendarInRegularSeason()) {
            System.out.println(matchSummaryOnCalendar);

            String stadium = teamService.findTeamByName(matchSummaryOnCalendar.getHomeTeam()).getStadium();

            // LocalDate에 시간 정보를 추가하여 LocalDateTime으로 변환
            LocalDateTime startDate = convertStringToLocalDateTime(matchSummaryOnCalendar.getDate()); // 자정 시간으로 변환

            Status status = Status.fromKorean(matchSummaryOnCalendar.getGameStatus());

            String note = "-";
            if (status == Status.CANCEL) {
                note = "경기 취소";
            }

            Game newGame = gameMapper.toNewGame(Season.REGULAR_SEASON, startDate, stadium, matchSummaryOnCalendar.getHomeScore(), matchSummaryOnCalendar.getAwayScore(), status, note);
            gameRepository.save(newGame);
            Team homeTeam = teamService.findTeamByName(matchSummaryOnCalendar.getHomeTeam());
            Team awayTeam = teamService.findTeamByName(matchSummaryOnCalendar.getAwayTeam());

            gameTeamService.createGameTeam(newGame, homeTeam, awayTeam);
        }
        // 포스트 시즌 생성
        for (FastAPIResponse.MatchSummaryOnCalendar matchSummaryOnCalendar : matchSummariesOnCalendar.getMatchSummariesOnCalendarInPostSeason()) {
            System.out.println(matchSummaryOnCalendar);

            String stadium = teamService.findTeamByName(matchSummaryOnCalendar.getHomeTeam()).getStadium();

            // LocalDate에 시간 정보를 추가하여 LocalDateTime으로 변환
            LocalDateTime startDate = convertStringToLocalDateTime(matchSummaryOnCalendar.getDate()); // 자정 시간으로 변환

            Status status = Status.fromKorean(matchSummaryOnCalendar.getGameStatus());

            String note = "-";
            if (status == Status.CANCEL) {
                note = "경기 취소";
            }

            Game newGame = gameMapper.toNewGame(Season.POST_SEASON, startDate, stadium, matchSummaryOnCalendar.getHomeScore(), matchSummaryOnCalendar.getAwayScore(), status, note);
            gameRepository.save(newGame);
            Team homeTeam = teamService.findTeamByName(matchSummaryOnCalendar.getHomeTeam());
            Team awayTeam = teamService.findTeamByName(matchSummaryOnCalendar.getAwayTeam());

            gameTeamService.createGameTeam(newGame, homeTeam, awayTeam);
        }
        return null;
    }

    @Override
    public GameResponse.GetGamesByDate getGamesByDate(String date) {
        LocalDateTime startDate = convertStringToLocalDateTime(date);
        System.out.println(startDate);

        List<Game> todayGames = gameRepository.findByStartDate(startDate);
        System.out.println(todayGames);
        return gameMapper.toGetGamesByDate(todayGames
                .stream()
                .map(game -> {
                    List<GameTeam> gameTeams = gameTeamService.findGameTeamsByGame(game);
                    // 각 게임의 homeTeam과 awayTeam을 찾아와야 함

                    Team homeTeam = null;
                    Team awayTeam = null;
                    for(GameTeam gameTeam : gameTeams) {
                        if (gameTeam.getHomeAway() == HomeAway.HOME) {
                            homeTeam = gameTeam.getTeam();
                        } else {
                            awayTeam = gameTeam.getTeam();
                        }
                    }

                    // gameMapper를 사용해 Game -> GameInfo로 변환
                    return gameMapper.toGameInfo(game, homeTeam, awayTeam);
                })
                .toList());
    }

    @Override
    public List<Integer> getScoresTest() {
        List<Game> games = gameRepository.findByStartDate(convertToStartDateTime(2024, 10, 1, 0, 0));
        return games.get(0).getHomeScores();
    }


    // String 형식의 Time 데이터를 Integer 변환
    public List<Integer> parseTimeStringToInteger(String date, String time) {
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


    public LocalDateTime convertStringToLocalDateTime(String dateString) {
        // 날짜 형식 지정 (yyyyMMdd)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 문자열을 LocalDate로 변환
        LocalDate date = LocalDate.parse(dateString, formatter);

        // 시간을 00:00:00으로 설정하여 LocalDateTime으로 변환
        return LocalDateTime.of(date, LocalTime.MIDNIGHT);
    }
}
