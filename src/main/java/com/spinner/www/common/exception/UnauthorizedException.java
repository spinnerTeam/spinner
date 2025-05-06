package com.spinner.www.common.exception;

import com.spinner.www.constants.CommonResultCode;
import lombok.Getter;

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
