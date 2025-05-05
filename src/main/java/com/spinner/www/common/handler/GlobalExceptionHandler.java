package com.spinner.www.common.handler;

import com.spinner.www.common.exception.*;
import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.util.ResponseVOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // 게시글 NOT_FOUND Exception 처리
    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<CommonResponse> handleBoardNotFoundException() {
        return new ResponseEntity<>(
                ResponseVOUtils.getFailResponse(CommonResultCode.NOT_FOUND_BOARD),
                HttpStatus.NOT_FOUND
        );
    }

    // 댓글 NOT_FOUND Exception 처리
    @ExceptionHandler(ReplyNotFoundException.class)
    public ResponseEntity<CommonResponse> handleReplyNotFoundException() {
        return new ResponseEntity<>(
                ResponseVOUtils.getFailResponse(CommonResultCode.NOT_FOUND_BOARD),
                HttpStatus.NOT_FOUND
        );
    }

    // 멤버 NOT_FOUND Exception 처리
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<CommonResponse> handleMemberNotFoundException() {
        return new ResponseEntity<>(
                ResponseVOUtils.getFailResponse(CommonResultCode.NOT_FOUND_MEMBER),
                HttpStatus.NOT_FOUND
        );
    }

    // 멤버 NOT_FOUND Exception 처리
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CommonResponse> UnauthorizedException() {
        return new ResponseEntity<>(
                ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED),
                HttpStatus.UNAUTHORIZED
        );
    }
}
