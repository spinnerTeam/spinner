package com.spinner.www.common.exception;

import com.spinner.www.constants.CommonResultCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "권한이 없습니다.")
@Getter
public class UnauthorizedException extends RuntimeException {
    private final CommonResultCode resultCode;

    public UnauthorizedException() {
        super(CommonResultCode.UNAUTHORIZED.message());
        this.resultCode = CommonResultCode.UNAUTHORIZED;
    }

    public UnauthorizedException(String message) {
        super(message);
        this.resultCode = CommonResultCode.UNAUTHORIZED;
    }

    public UnauthorizedException(CommonResultCode resultCode) {
        super(resultCode.message());
        this.resultCode = resultCode;
    }
}
