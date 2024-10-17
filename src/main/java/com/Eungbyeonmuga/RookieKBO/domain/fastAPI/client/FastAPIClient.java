package com.Eungbyeonmuga.RookieKBO.domain.fastAPI.client;

import com.Eungbyeonmuga.RookieKBO.config.FeignConfig;
import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.dto.FastAPIRequest;
import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.dto.FastAPIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "FastAPIClient",
        // TODO: Local/Dev 환경에서 url 서로 바꿔줘야함. 추후에 FastAPI 도커로 옮겨서 Spring과 같은 네트워크 사용하게 수정할 것.
        url = "http://rookiekbo.store:8000",
        configuration = FeignConfig.class
)

public interface FastAPIClient {
    @PostMapping(value = "/games/all-season")
    public FastAPIResponse.GetGames getGames(
            @RequestBody FastAPIRequest.GetGames request
            );

    @PostMapping(value = "/games/calendar/all-season")
    public FastAPIResponse.GetGameSummariesOnCalendar getGameSummariesOnCalendar(
            @RequestBody FastAPIRequest.GetGameSummariesOnCalendar request
    );

    @PostMapping(value = "/games/detail")
    public FastAPIResponse.GetGameDetailByDate getGameDetailByDate(
            @RequestBody FastAPIRequest.GetGameDetailByDate request
    );

}
