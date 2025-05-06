package com.spinner.www.common.exception;

import com.spinner.www.constants.CommonResultCode;
import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {
    private final CommonResultCode resultCode;

    public ForbiddenException() {
        super(CommonResultCode.FORBIDDEN.message());
        this.resultCode = CommonResultCode.FORBIDDEN;
    }

    public ForbiddenException(String message) {
        super(message);
        this.resultCode = CommonResultCode.FORBIDDEN;
    }

    public ForbiddenException(CommonResultCode resultCode) {
        super(resultCode.message());
        this.resultCode = resultCode;
    }
}
