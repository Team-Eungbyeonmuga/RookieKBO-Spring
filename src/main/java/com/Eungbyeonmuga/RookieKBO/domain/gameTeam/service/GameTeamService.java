package com.Eungbyeonmuga.RookieKBO.domain.gameTeam.service;

import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Game;
import com.Eungbyeonmuga.RookieKBO.domain.gameTeam.entity.GameTeam;
import com.Eungbyeonmuga.RookieKBO.domain.team.entity.Team;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GameTeamService {
    @Transactional
    List<GameTeam> createGameTeam(Game game, Team home, Team away);

    List<GameTeam> findGameTeamsByGame(Game game);
}
