package com.Eungbyeonmuga.RookieKBO.domain.team.service;

import com.Eungbyeonmuga.RookieKBO.domain.team.entity.Team;
import com.Eungbyeonmuga.RookieKBO.domain.team.repository.TeamRepository;
import com.Eungbyeonmuga.RookieKBO.global.exception.RestApiException;
import com.Eungbyeonmuga.RookieKBO.global.exception.errorCode.TeamErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    // 고정된 팀 데이터
    private final List<Team> teams = List.of(
            Team.builder().name("두산").stadium("잠실 야구장").build(),
            Team.builder().name("LG").stadium("잠실 야구장").build(),
            Team.builder().name("키움").stadium("고척 스카이돔").build(),
            Team.builder().name("SSG").stadium("인천 SSG 랜더스필드").build(),
            Team.builder().name("NC").stadium("창원 NC 파크").build(),
            Team.builder().name("삼성").stadium("대구 삼성 라이온즈 파크").build(),
            Team.builder().name("한화").stadium("대전 한화생명이글스파크").build(),
            Team.builder().name("KIA").stadium("광주-기아 챔피언스 필드").build(),
            Team.builder().name("롯데").stadium("사직 야구장").build(),
            Team.builder().name("KT").stadium("수원 KT 위즈파크").build(),
            Team.builder().name("PO승리팀").stadium("미정").build()
    );

    @Override
    public Team findTeamByName(String name) {
        return teamRepository.findTeamByName(name).orElseThrow(() -> new RestApiException(TeamErrorCode.TEAM_NOT_FOUND));
    }

    @Override
    public String setTeams() {
        teamRepository.saveAll(teams);
        return "Success Save Teams";
    }
}
