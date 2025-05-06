package com.spinner.www.common.exception;

import com.spinner.www.constants.CommonResultCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "올바르지 않은 접근입니다.")
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
