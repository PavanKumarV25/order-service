package com.tech.OrderService.exception;

import com.tech.OrderService.exception.external.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseentityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException exception)
    {
        return new ResponseEntity<>(new ErrorResponse().builder().errorCode(exception.getErrorCode()).errorMessage(exception.getMessage()).build(), HttpStatus.NOT_FOUND);
    }

}
