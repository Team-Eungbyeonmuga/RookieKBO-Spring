package com.Eungbyeonmuga.RookieKBO.global.exception.errorCode;

import com.Eungbyeonmuga.RookieKBO.global.exception.ErrorCode;
import com.Eungbyeonmuga.RookieKBO.global.exception.ErrorCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TeamErrorCode implements ErrorCodeInterface {
    TEAM_NOT_FOUND("TEAM001", "팀을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.builder()
                .code(code)
                .message(message)
                .httpStatus(httpStatus)
                .build();
    }
}
