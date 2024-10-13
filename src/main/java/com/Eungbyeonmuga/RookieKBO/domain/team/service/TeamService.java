package com.Eungbyeonmuga.RookieKBO.domain.team.service;

import com.Eungbyeonmuga.RookieKBO.domain.team.entity.Team;

public interface TeamService {
    Team findTeamByName(String name);

    String setTeams();
}
