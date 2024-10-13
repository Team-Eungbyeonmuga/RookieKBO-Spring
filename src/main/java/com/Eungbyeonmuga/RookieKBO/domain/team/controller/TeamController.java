package com.Eungbyeonmuga.RookieKBO.domain.team.controller;

import com.Eungbyeonmuga.RookieKBO.domain.team.service.TeamService;
import com.Eungbyeonmuga.RookieKBO.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;
    @GetMapping("/set-teams")
    public BaseResponse<String> setTeams() {
        return BaseResponse.onSuccess(teamService.setTeams());
    }
}
