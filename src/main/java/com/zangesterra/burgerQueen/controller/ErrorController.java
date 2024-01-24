package com.zangesterra.burgerQueen.controller;

import com.zangesterra.burgerQueen.dto.response.WebResponse;
import com.zangesterra.burgerQueen.exception.StockNotEnoughException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> responseStatusException(ResponseStatusException exception){
        return ResponseEntity.status(exception.getStatus())
                .body(WebResponse.<String>builder().errors(exception.getReason()).build());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(StockNotEnoughException.class)
    public WebResponse<String> stockNotEnoughException(StockNotEnoughException exception){
        return WebResponse.<String>builder().data(exception.getMessage()).build();
    }
}
