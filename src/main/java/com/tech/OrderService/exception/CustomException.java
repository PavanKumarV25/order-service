package com.tech.OrderService.exception;

import feign.codec.ErrorDecoder;
import lombok.Data;

@Data
public class CustomException extends RuntimeException {

    private String errorCode;

    private String errorMessage;

    public CustomException(String errorMessage,String errorCode,int status)
    {
        super(errorMessage);
        this.errorMessage=errorMessage;
        this.errorCode=errorCode;
    }

}
