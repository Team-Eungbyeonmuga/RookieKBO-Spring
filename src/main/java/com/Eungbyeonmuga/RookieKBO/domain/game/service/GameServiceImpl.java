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
    private final GameMapper gameMapper;
    private final GameTeamService gameTeamService;



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
