package com.spinner.www.common.exception;

import com.spinner.www.constants.CommonResultCode;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
    private final CommonResultCode resultCode;

    public BadRequestException() {
        super(CommonResultCode.BAD_REQUEST.message());
        this.resultCode = CommonResultCode.BAD_REQUEST;
    }

    public BadRequestException(String message) {
        super(message);
        this.resultCode = CommonResultCode.BAD_REQUEST;
    }

    public BadRequestException(CommonResultCode resultCode) {
        super(resultCode.message());
        this.resultCode = resultCode;
    }
}
