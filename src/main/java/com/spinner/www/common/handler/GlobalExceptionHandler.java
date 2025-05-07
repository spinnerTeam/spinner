package com.spinner.www.common.handler;

import com.spinner.www.common.exception.*;
import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.util.ResponseVOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<CommonResponse> buildErrorResponse(int code,
                                                              String message,
                                                              HttpStatus status) {
        return new ResponseEntity<>(ResponseVOUtils.getFailResponse(code, message), status);
    }

    // NullPointerException 처리
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<CommonResponse> handleNullPointerException(NullPointerException ex) {
        log.error("NullPointerException occurred: ", ex);
        return buildErrorResponse(CommonResultCode.DATA_NOT_FOUND.code(),
                ex.getMessage(),
                HttpStatus.NOT_FOUND);
    }

    // RuntimeException 처리
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResponse> handleRuntimeException(RuntimeException ex) {
        log.error("RuntimeException occurred: ", ex);
        return buildErrorResponse(CommonResultCode.RUNTIME_EXCEPTION.code(),
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // IllegalArgumentException 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("IllegalArgumentException occurred: ", ex);
        return buildErrorResponse(CommonResultCode.BAD_REQUEST.code(),
            ex.getMessage(),
            HttpStatus.BAD_REQUEST);
    }

    // 정의되지 않은 Exception 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleException(Exception ex) {
        log.error("Exception occurred: ", ex);
        return buildErrorResponse(CommonResultCode.ERROR.code(),
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 스터디 NOT_FOUND Exception 처리
    @ExceptionHandler(StudyNotFoundException.class)
    public ResponseEntity<CommonResponse> handleStudyNotFoundException(StudyNotFoundException ex) {
        return new ResponseEntity<>(
                ResponseVOUtils.getFailResponse(CommonResultCode.NOT_FOUND_STUDY),
                HttpStatus.NOT_FOUND
        );
    }

    // 공통 NOT_FOUND Exception 처리
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonResponse> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(
                ResponseVOUtils.getFailResponse(ex.getResultCode()),
                HttpStatus.NOT_FOUND
        );
    }

    // 올바르지 않은 접근 Exception 처리
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CommonResponse> handleForbiddenException(ForbiddenException ex) {
        return new ResponseEntity<>(
                ResponseVOUtils.getFailResponse(ex.getResultCode()),
                HttpStatus.FORBIDDEN
        );
    }

    // 멤버 NOT_FOUND Exception 처리
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CommonResponse> handleUnauthorizedException(UnauthorizedException ex) {
        return new ResponseEntity<>(
                ResponseVOUtils.getFailResponse(ex.getResultCode()),
                HttpStatus.UNAUTHORIZED
        );
    }

    // 로그인 안되어 있을때 예외 처리
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonResponse> handleAccessDeniedException() {
        return new ResponseEntity<>(
                ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED),
                HttpStatus.UNAUTHORIZED
        );
    }

    // BadRequest 예외 처리
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CommonResponse> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(
                ResponseVOUtils.getFailResponse(ex.getResultCode()),
                HttpStatus.BAD_REQUEST
        );
    }
}
