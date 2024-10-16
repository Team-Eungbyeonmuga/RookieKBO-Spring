package com.Eungbyeonmuga.RookieKBO.domain.fastAPI.controller;

import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.service.FastAPIService;
import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameRequest;
import com.Eungbyeonmuga.RookieKBO.domain.game.dto.GameResponse;
import com.Eungbyeonmuga.RookieKBO.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fast-apis")
public class FastAPIController {

    private final FastAPIService fastAPIService;

    @PostMapping("/games")
    public BaseResponse<GameResponse.CreateGameBaseInfoForMonthUsingFastAPI> getGames(
            @RequestBody GameRequest.CreateGameBaseInfoForMonthUsingFastAPI request
    ) {
        return BaseResponse.onSuccess(fastAPIService.createGameBaseInfoForMonthUsingFastAPIFromList(request.getYear(), request.getMonth()));
    }

    @PostMapping("/games/calendar")
    public BaseResponse<GameResponse.CreateGameBaseInfoForMonthUsingFastAPI> getGameSummariesOnCalendar(
            @RequestBody GameRequest.CreateGameBaseInfoForMonthUsingFastAPI request
    ) {
        return BaseResponse.onSuccess(fastAPIService.createGameBaseInfoForMonthUsingFastAPIFromCalendar(request.getYear(), request.getMonth()));
    }

    @PostMapping("/update/detail")
    public BaseResponse<GameResponse.UpdateGamesDetailByDate> updateGamesDetailByDate(
            @RequestBody GameRequest.UpdateGamesDetailByDate request
    ) {
        return BaseResponse.onSuccess(fastAPIService.updateGamesDetailByDate(request.getYear(), request.getMonth(), request.getDay()));
    }
}
