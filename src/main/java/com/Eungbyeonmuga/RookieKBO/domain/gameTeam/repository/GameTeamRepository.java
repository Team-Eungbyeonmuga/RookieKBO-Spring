package com.Eungbyeonmuga.RookieKBO.domain.gameTeam.repository;

import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Game;
import com.Eungbyeonmuga.RookieKBO.domain.gameTeam.entity.GameTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameTeamRepository extends JpaRepository<GameTeam, Long> {

    List<GameTeam> findGameTeamsByGame(Game game);
}
