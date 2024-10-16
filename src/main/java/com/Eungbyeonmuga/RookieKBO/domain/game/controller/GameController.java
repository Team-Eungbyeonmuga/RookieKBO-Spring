package com.Eungbyeonmuga.RookieKBO.domain.game.controller;

import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameRequest;
import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameResponse;
import com.Eungbyeonmuga.RookieKBO.domain.game.service.GameService;
import com.Eungbyeonmuga.RookieKBO.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @PostMapping("/get-games")
    public BaseResponse<GameResponse.GetGamesByYearAndMonth> getGames(
            @RequestBody GameRequest.GetGames request
            ) {
        return BaseResponse.onSuccess(gameService.createGameBaseInfoForMonthFromKBOList(request.getYear(), request.getMonth()));
    }

    @PostMapping("/get-games/calendar")
    public BaseResponse<GameResponse.GetGamesByYearAndMonth> getGameSummariesOnCalendar(
            @RequestBody GameRequest.GetGames request
    ) {
        return BaseResponse.onSuccess(gameService.createGameBaseInfoForMonthFromKBOCalendar(request.getYear(), request.getMonth()));
    }

    @PostMapping
    public BaseResponse<GameResponse.GetGamesByDate> getGamesByDate(
            @RequestBody GameRequest.GetGamesByDate request
    ) {
        return BaseResponse.onSuccess(gameService.getGamesByDate(request.getDate()));
    }

    @PostMapping("/update/detail")
    public BaseResponse<GameResponse.UpdateGamesDetailByDate> updateGamesDetailByDate(
            @RequestBody GameRequest.UpdateGamesDetailByDate request
    ) {
        return BaseResponse.onSuccess(gameService.updateGamesDetailByDate(request.getYear(), request.getMonth(), request.getDay()));
    }

    @GetMapping("/get-scores-test")
    public BaseResponse<List<Integer>> getScoresTest() {
        return BaseResponse.onSuccess(gameService.getScoresTest());
    }
}
