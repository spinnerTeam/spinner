package com.spinner.www.common.exception;

import com.spinner.www.constants.CommonResultCode;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final CommonResultCode resultCode;

    public NotFoundException() {
        super(CommonResultCode.DATA_NOT_FOUND.message());
        this.resultCode = CommonResultCode.DATA_NOT_FOUND;
    }

    public NotFoundException(String message) {
        super(message);
        this.resultCode = CommonResultCode.DATA_NOT_FOUND;
    }

    public NotFoundException(CommonResultCode resultCode) {
        super(resultCode.message());
        this.resultCode = resultCode;
    }
}
