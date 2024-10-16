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

    @PostMapping
    public BaseResponse<GameResponse.GetGamesByDate> getGamesByDate(
            @RequestBody GameRequest.GetGamesByDate request
    ) {
        return BaseResponse.onSuccess(gameService.getGamesByDate(request.getDate()));
    }


    @GetMapping("/get-scores-test")
    public BaseResponse<List<Integer>> getScoresTest() {
        return BaseResponse.onSuccess(gameService.getScoresTest());
    }
}
