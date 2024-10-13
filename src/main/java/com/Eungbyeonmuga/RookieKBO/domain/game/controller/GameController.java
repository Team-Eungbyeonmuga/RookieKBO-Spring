package com.Eungbyeonmuga.RookieKBO.domain.game.controller;

import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameRequest;
import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameResponse;
import com.Eungbyeonmuga.RookieKBO.domain.game.service.GameService;
import com.Eungbyeonmuga.RookieKBO.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @PostMapping("/get-matches")
    public BaseResponse<GameResponse.GetMatches> getMatches(
            @RequestBody GameRequest.GetMatches request
            ) {
        return BaseResponse.onSuccess(gameService.getMatchesByYearAndMonthAndSeason(request.getYear(), request.getMonth(), request.getSeason()));
    }
}
