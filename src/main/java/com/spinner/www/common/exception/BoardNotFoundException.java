package com.spinner.www.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException(String message) {
        super(message);
    }
}
