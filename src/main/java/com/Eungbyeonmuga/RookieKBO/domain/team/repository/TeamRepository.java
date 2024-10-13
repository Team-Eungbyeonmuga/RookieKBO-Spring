package com.Eungbyeonmuga.RookieKBO.domain.team.repository;

import com.Eungbyeonmuga.RookieKBO.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findTeamByName(String name);
}