package com.Eungbyeonmuga.RookieKBO.domain.gameTeam.service;

import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Game;
import com.Eungbyeonmuga.RookieKBO.domain.gameTeam.entity.GameTeam;
import com.Eungbyeonmuga.RookieKBO.domain.gameTeam.entity.HomeAway;
import com.Eungbyeonmuga.RookieKBO.domain.gameTeam.mapper.GameTeamMapper;
import com.Eungbyeonmuga.RookieKBO.domain.gameTeam.repository.GameTeamRepository;
import com.Eungbyeonmuga.RookieKBO.domain.team.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameTeamServiceImpl implements GameTeamService {

    private final GameTeamRepository gameTeamRepository;
    private final GameTeamMapper gameTeamMapper;

    @Override
    @Transactional
    public List<GameTeam> createGameTeam(Game game, Team home, Team away) {
        List<GameTeam> gameTeams = new ArrayList<>();
        GameTeam gameHomeTeam = gameTeamMapper.toGameTeam(game, home, HomeAway.HOME);
        GameTeam gameAwayTeam = gameTeamMapper.toGameTeam(game, away, HomeAway.AWAY);
        gameTeams.add(gameHomeTeam);
        gameTeams.add(gameAwayTeam);
        gameTeamRepository.saveAll(gameTeams);
        return gameTeams;
    }

    @Override
    public List<GameTeam> findGameTeamsByGame(Game game) {
        return gameTeamRepository.findGameTeamsByGame(game);
    }
}
