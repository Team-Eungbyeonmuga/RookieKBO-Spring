package com.Eungbyeonmuga.RookieKBO.domain.fastAPI.client;

import com.Eungbyeonmuga.RookieKBO.config.FeignConfig;
import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.dto.FastAPIRequest;
import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.dto.FastAPIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "FastAPIClient",
        url = "http://localhost:8000",
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

}
