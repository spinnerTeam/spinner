package com.spinner.www.common.exception;

import com.spinner.www.constants.CommonResultCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "올바르지 못한 요청입니다.")
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
