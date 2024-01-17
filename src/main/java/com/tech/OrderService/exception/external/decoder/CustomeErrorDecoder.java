package com.tech.OrderService.exception.external.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.OrderService.exception.CustomException;
import com.tech.OrderService.exception.external.response.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class CustomeErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {

        ObjectMapper objectMapper = new ObjectMapper();
        Logger log=LoggerFactory.getLogger(CustomeErrorDecoder.class);
        
        		log.info("::{}",response.request().url());
        log.info("::{}",response.request().headers());

        try {
            ErrorResponse errorResponse = objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);
            return new CustomException(errorResponse.getErrorMessage(),errorResponse.getErrorCode(),response.status());
        }
        catch (IOException e)
        {
            return new CustomException("Internal Server Error","INTERNAL_SERVER_ERROR",500);
        }



    }
}
