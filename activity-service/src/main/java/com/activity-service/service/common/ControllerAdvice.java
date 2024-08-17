package com.mod2.service.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(LetstockException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> letstockExeption(LetstockException e) {
        log.error("LetstockException: {}", e.getMessage(), e);

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(e.getStatusCode()))
                .message(e.getMessage())
                .build();

        if (e instanceof InvalidRequest invalidRequest) {
            body.addValidation(invalidRequest.getValidation());
        }

        return ResponseEntity.status(e.getStatusCode())
                .body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException: {}", e.getMessage(), e);

        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다")
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            ErrorValidation validation = ErrorValidation.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();

            response.addValidation(validation);
        }
        return response;
    }
}
