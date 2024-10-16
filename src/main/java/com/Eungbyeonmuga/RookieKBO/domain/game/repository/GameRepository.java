package com.Eungbyeonmuga.RookieKBO.domain.game.repository;

import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Game;
import com.Eungbyeonmuga.RookieKBO.domain.game.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g FROM Game g " +
            "LEFT JOIN GameTeam gtHome ON g.id = gtHome.game.id AND gtHome.homeAway = com.Eungbyeonmuga.RookieKBO.domain.gameTeam.entity.HomeAway.HOME " +
            "LEFT JOIN GameTeam gtAway ON g.id = gtAway.game.id AND gtAway.homeAway = com.Eungbyeonmuga.RookieKBO.domain.gameTeam.entity.HomeAway.AWAY " +
            "WHERE FUNCTION('DATE', g.startDateTime) = :startDateTime " +
            "AND (gtHome.team.name = :homeTeam OR gtHome.team IS NULL) " +
            "AND (gtAway.team.name = :awayTeam OR gtAway.team IS NULL)")
    Game findByStartDateTimeAndTeams(LocalDateTime startDateTime, String homeTeam, String awayTeam);

    List<Game> findGamesByStartDateTimeAndSeason(LocalDateTime startDateTime, Season season);

    @Query("SELECT g FROM Game g " +
            "WHERE FUNCTION('DATE', g.startDateTime) = FUNCTION('DATE', :startDate) ")
    List<Game> findByStartDate(LocalDateTime startDate);

}
