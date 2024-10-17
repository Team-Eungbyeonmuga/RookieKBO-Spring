package com.Eungbyeonmuga.RookieKBO.domain.fastAPI.service;

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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FastAPIServiceImpl implements FastAPIService {

    private final FastAPIClient fastAPIClient;
    private final TeamService teamService;

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final GameTeamService gameTeamService;

    // TODO: 코드 리팩토링 필요
    // 우천 취소와 같은 기타 상황을 위한 기본 Game 셋팅

    /***
     * 사용 X KBO 리스트 기반인데, 데이터가 정확하지 않아서 캘린더 기반으로 변경
     * @param year
     * @param month
     */
    @Override
    public GameResponse.CreateGameBaseInfoForMonthUsingFastAPI createGameBaseInfoForMonthUsingFastAPIFromList(Integer year, Integer month) {

        FastAPIRequest.GetGames getGamesRequest = FastAPIRequest.GetGames
                .builder()
                .year(year)
                .month(month)
                .build();

        // FastAPI 서버에서 클로링 후 Game 생성 및 GameTeam 생성
        FastAPIResponse.GetGames gameSummaries = fastAPIClient.getGames(getGamesRequest);
        System.out.println(gameSummaries);
        // 정규 시즌 생성
        for (FastAPIResponse.GameSummary gameSummary : gameSummaries.getGameSummariesInRegularSeason()) {
            System.out.println(gameSummary);
            List<Integer> timeIntegerList = parseTimeStringToInteger(gameSummary.getDate(), gameSummary.getTime());
            LocalDateTime startDateTime = convertToStartDateTime(year, month, timeIntegerList.get(0), timeIntegerList.get(1), timeIntegerList.get(2));
            Status status;
            if (gameSummary.getNote().contains("취소")) {
                status = Status.CANCEL;
            } else {
                if (LocalDateTime.now().plusMinutes(5).isAfter(startDateTime)) {
                    status = Status.OVER;
                } else {
                    status = Status.PREPARING;
                }
            }
            Game newGame = gameMapper.toNewGame(Season.REGULAR_SEASON, startDateTime, gameSummary.getPlace(), gameSummary.getHomeScore(), gameSummary.getAwayScore(), status, gameSummary.getNote());
            gameRepository.save(newGame);
            Team homeTeam = teamService.findTeamByName(gameSummary.getHomeTeam());
            Team awayTeam = teamService.findTeamByName(gameSummary.getAwayTeam());

            gameTeamService.createGameTeam(newGame, homeTeam, awayTeam);
        }
        // 포스트 시즌 생성
        for (FastAPIResponse.GameSummary gameSummary : gameSummaries.getGameSummariesInPostSeason()) {
            System.out.println(gameSummary);
            List<Integer> timeIntegerList = parseTimeStringToInteger(gameSummary.getDate(), gameSummary.getTime());
            LocalDateTime startDateTime = convertToStartDateTime(year, month, timeIntegerList.get(0), timeIntegerList.get(1), timeIntegerList.get(2));
            Status status;
            if (gameSummary.getNote().contains("취소")) {
                status = Status.CANCEL;
            } else {
                if (LocalDateTime.now().plusMinutes(5).isAfter(startDateTime)) {
                    status = Status.OVER;
                } else {
                    status = Status.PREPARING;
                }
            }
            Game newGame = gameMapper.toNewGame(Season.POST_SEASON, startDateTime, gameSummary.getPlace(), gameSummary.getHomeScore(), gameSummary.getAwayScore(), status, gameSummary.getNote());
            gameRepository.save(newGame);
            Team homeTeam = teamService.findTeamByName(gameSummary.getHomeTeam());
            Team awayTeam = teamService.findTeamByName(gameSummary.getAwayTeam());

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
    public GameResponse.CreateGameBaseInfoForMonthUsingFastAPI createGameBaseInfoForMonthUsingFastAPIFromCalendar(Integer year, Integer month) {

        List<Game> games = gameRepository.findByStartDate(convertToStartDateTime(year, month, 1, 0, 0));
        FastAPIRequest.GetGameSummariesOnCalendar getGameSummariesOnCalendar = FastAPIRequest.GetGameSummariesOnCalendar
                .builder()
                .year(year)
                .month(month)
                .build();


        // FastAPI 서버에서 클로링 후 Game 생성 및 GameTeam 생성
        FastAPIResponse.GetGameSummariesOnCalendar gameSummariesOnCalendar = fastAPIClient.getGameSummariesOnCalendar(getGameSummariesOnCalendar);
        System.out.println(gameSummariesOnCalendar);
        // 정규 시즌 생성
        for (FastAPIResponse.GameSummaryOnCalendar gameSummaryOnCalendar : gameSummariesOnCalendar.getGameSummariesOnCalendarInRegularSeason()) {
            System.out.println(gameSummaryOnCalendar);

            String stadium = teamService.findTeamByName(gameSummaryOnCalendar.getHomeTeam()).getStadium();

            // LocalDate에 시간 정보를 추가하여 LocalDateTime으로 변환
            LocalDateTime startDate = convertStringToLocalDateTime(gameSummaryOnCalendar.getDate()); // 자정 시간으로 변환

            Status status = Status.fromKorean(gameSummaryOnCalendar.getGameStatus());

            String note = "-";
            if (status == Status.CANCEL) {
                note = "경기 취소";
            }

            Game newGame = gameMapper.toNewGame(Season.REGULAR_SEASON, startDate, stadium, gameSummaryOnCalendar.getHomeScore(), gameSummaryOnCalendar.getAwayScore(), status, note);
            gameRepository.save(newGame);
            Team homeTeam = teamService.findTeamByName(gameSummaryOnCalendar.getHomeTeam());
            Team awayTeam = teamService.findTeamByName(gameSummaryOnCalendar.getAwayTeam());

            gameTeamService.createGameTeam(newGame, homeTeam, awayTeam);
        }
        // 포스트 시즌 생성
        for (FastAPIResponse.GameSummaryOnCalendar gameSummaryOnCalendar : gameSummariesOnCalendar.getGameSummariesOnCalendarInPostSeason()) {
            System.out.println(gameSummaryOnCalendar);

            String stadium = teamService.findTeamByName(gameSummaryOnCalendar.getHomeTeam()).getStadium();

            // LocalDate에 시간 정보를 추가하여 LocalDateTime으로 변환
            LocalDateTime startDate = convertStringToLocalDateTime(gameSummaryOnCalendar.getDate()); // 자정 시간으로 변환

            Status status = Status.fromKorean(gameSummaryOnCalendar.getGameStatus());

            String note = "-";
            if (status == Status.CANCEL) {
                note = "경기 취소";
            }

            Game newGame = gameMapper.toNewGame(Season.POST_SEASON, startDate, stadium, gameSummaryOnCalendar.getHomeScore(), gameSummaryOnCalendar.getAwayScore(), status, note);
            gameRepository.save(newGame);
            Team homeTeam = teamService.findTeamByName(gameSummaryOnCalendar.getHomeTeam());
            Team awayTeam = teamService.findTeamByName(gameSummaryOnCalendar.getAwayTeam());

            gameTeamService.createGameTeam(newGame, homeTeam, awayTeam);
        }
        return null;
    }

    @Override
    @Transactional
    public GameResponse.UpdateGamesDetailByDate updateGamesDetailByDate(Integer year, Integer month, Integer day) {

        FastAPIRequest.GetGameDetailByDate request = FastAPIRequest.GetGameDetailByDate.builder()
                .year(year)
                .month(month)
                .day(day)
                .build();

        FastAPIResponse.GetGameDetailByDate getGameDetailByDate = fastAPIClient.getGameDetailByDate(request);

        for (FastAPIResponse.GameDetail gameDetail : getGameDetailByDate.getGameDetails()) {

            List<Game> games = gameRepository.findByStartDateTimeAndTeams(LocalDateTime.parse(gameDetail.getStartDateTime()), gameDetail.getHomeTeam(), gameDetail.getAwayTeam());
            Game game = null;
            if (!games.isEmpty()) {
                game = games.get(0);
            }
            System.out.println(game);
            if (game == null) {
                return GameResponse.UpdateGamesDetailByDate.builder()
                        .resultMessage("기본 경기 데이터가 없습니다. 월 단위 경기 기본 데이터를 생성한 후 다시 호출하세요.")
                        .build();
            }

            game.updateHomeScores(gameDetail.getHomeScores());
            game.updateAwayScores(gameDetail.getAwayScores());
            game.updateHomeRHEB(gameDetail.getHomeRHEB());
            game.updateAwayRHEB(gameDetail.getAwayRHEB());
            game.updateStatus(Status.fromKorean(gameDetail.getGameStatus()));
            game.updateStartDateTime(LocalDateTime.parse(gameDetail.getStartDateTime()));
        }

        System.out.println(getGameDetailByDate);
        return GameResponse.UpdateGamesDetailByDate.builder()
                .resultMessage("데이터 업데이트 성공")
                .build();
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
