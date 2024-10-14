package com.Eungbyeonmuga.RookieKBO.domain.fastAPI.client;

import com.Eungbyeonmuga.RookieKBO.config.FeignConfig;
import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.dto.FastAPIRequest;
import com.Eungbyeonmuga.RookieKBO.domain.fastAPI.dto.FastAPIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "FastAPIClient",
        url = "http://localhost:8000",
        configuration = FeignConfig.class
)

public interface FastAPIClient {
    @PostMapping(value = "/matches/all-season")
    public FastAPIResponse.GetMatches getMatches(
            @RequestBody FastAPIRequest.GetMatches request
            );

//    @PostMapping(value = "/playlist")
//    public FastAPIResponse.YTMusicApiExportPlaylistResponse exportPlaylist(
//            @RequestBody YTMusicApiRequest.YTMusicAPiExportPlaylistRequest request
//    );

}
