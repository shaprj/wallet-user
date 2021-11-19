package com.wallet.user.exception;

import com.wallet.user.api.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
class GlobalDefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("#{new Boolean('${exceptions.print_stack_trace}')}")
    Boolean printStackTrace;

    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        final StringBuilder description = new StringBuilder();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            description.append(String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return buildResponseEntity(ex, HttpStatus.UNPROCESSABLE_ENTITY, description.toString());
    }

    @Override
    public ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        return buildErrorResponse(ex, status, request);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception ex, HttpStatus status, WebRequest request) {

        log.error(ExceptionUtils.getStackTrace(ex));

        return buildResponseEntity(ex, status);
    }

    private ResponseEntity<Object> buildResponseEntity(Exception ex, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(ErrorResponse.builder()
                        .message(ex.getMessage())
                        .stackTrace(printStackTrace ? ExceptionUtils.getStackTrace(ex) : "")
                        .build());
    }

    private ResponseEntity<Object> buildResponseEntity(Exception ex, HttpStatus status, String description) {
        return ResponseEntity
                .status(status)
                .body(ErrorResponse.builder()
                        .message(ex.getMessage())
                        .stackTrace(printStackTrace ? ExceptionUtils.getStackTrace(ex) : "")
                        .description(description != null ? description : "")
                        .build());
    }
}