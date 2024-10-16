package com.Eungbyeonmuga.RookieKBO.domain.gameTeam.mapper;

import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Game;
import com.Eungbyeonmuga.RookieKBO.domain.gameTeam.entity.GameTeam;
import com.Eungbyeonmuga.RookieKBO.domain.gameTeam.entity.HomeAway;
import com.Eungbyeonmuga.RookieKBO.domain.team.entity.Team;
import org.springframework.stereotype.Component;

@Component
public class GameTeamMapper {

    public GameTeam toGameTeam(Game game, Team team, HomeAway homeAway) {
        return GameTeam.builder()
                .game(game)
                .team(team)
                .homeAway(homeAway)
                .build();
    }
}
