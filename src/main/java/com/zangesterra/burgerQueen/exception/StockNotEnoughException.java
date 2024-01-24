package com.zangesterra.burgerQueen.exception;

public class StockNotEnoughException extends Exception{

    StockNotEnoughException(String errorMessage){
        super(errorMessage);
    }
}
