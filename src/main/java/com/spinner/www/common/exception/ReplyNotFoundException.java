package com.spinner.www.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReplyNotFoundException extends RuntimeException {
    public ReplyNotFoundException(String message) {
        super(message);
    }
}
