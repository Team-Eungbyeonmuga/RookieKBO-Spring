package com.Eungbyeonmuga.RookieKBO.domain.game.service;

import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.client.FastAPIClient;
import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.dto.FastAPIRequest;
import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.dto.FastAPIResponse;
import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameRequest;
import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameResponse;
import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Game;
import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Season;
import com.Eungbyeonmuga.RookieKBO.domain.game.mapper.GameMapper;
import com.Eungbyeonmuga.RookieKBO.domain.game.repository.GameRepository;
import com.Eungbyeonmuga.RookieKBO.domain.gameTeam.entity.GameTeam;
import com.Eungbyeonmuga.RookieKBO.domain.gameTeam.service.GameTeamService;
import com.Eungbyeonmuga.RookieKBO.domain.team.entity.Team;
import com.Eungbyeonmuga.RookieKBO.domain.team.repository.TeamRepository;
import com.Eungbyeonmuga.RookieKBO.domain.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Override
    public GameResponse.GetMatches getMatchesByYearAndMonthAndSeason(Integer year, Integer month, String season) {

        List<Game> games = gameRepository.findByStartDateAndSeason(convertToStartDateTime(year, month, 1, 0, 0), Season.fromKorean(season));
        FastAPIRequest.GetMatches getMatchesRequest;

        // 이전에 크롤링되지 않아서 Spring DB에 없을 때
        if(games.isEmpty()) {
            getMatchesRequest = FastAPIRequest.GetMatches
                    .builder()
                    .year(year)
                    .month(month)
                    .season(season)
                    .build();
            // FastAPI 서버에서 클로링 후 Game 생성 및 GameTeam 생성
            FastAPIResponse.GetMatches getMatches = fastAPIClient.getMatches(getMatchesRequest);
            for(FastAPIResponse.MatchInfo matchInfo : getMatches.getMatchInfos()) {
                String[] timeParts = matchInfo.getTime().split(":");
                String[] dayParts = matchInfo.getDay().split("\\(");
                String dayString = dayParts[0];  // "10.02"
                String[] dayMonthParts = dayString.split("\\.");  // "10.02" -> ["10", "02"]
                Integer day = Integer.parseInt(dayMonthParts[1]);  // "02" -> 2
                Integer hour = Integer.parseInt(timeParts[0]);
                Integer minute = Integer.parseInt(timeParts[1]);
                LocalDateTime startDateTime = convertToStartDateTime(year, month, day, hour, minute);
                Season enumSeason = Season.fromKorean(season);
                Game newGame = gameMapper.toGame(enumSeason, startDateTime, matchInfo.getPlace());
//                games.add(newGame);
                gameRepository.save(newGame);
                System.out.println(matchInfo.getHomeTeam());
                System.out.println(matchInfo.getAwayTeam());
                Team homeTeam = teamService.findTeamByName(matchInfo.getHomeTeam());
                Team awayTeam = teamService.findTeamByName(matchInfo.getAwayTeam());

                gameTeamService.createGameTeam(newGame, homeTeam, awayTeam);
            }
//            gameRepository.saveAll(games);
            return gameMapper.toGetMatches(fastAPIClient.getMatches(getMatchesRequest));
        } else {
            return null;
        }




    }

    public LocalDateTime convertToStartDateTime(int year, int month, int day, int hour, int minute) {
        // day가 0이면 기본값으로 1을 설정
        day = (day == 0) ? 1 : day;

        // 해당 년, 월, 일, 시간, 분으로 LocalDateTime 생성
        return LocalDateTime.of(year, month, day, hour, minute);
    }
}
