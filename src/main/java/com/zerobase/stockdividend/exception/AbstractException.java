package com.zerobase.stockdividend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class AbstractException extends RuntimeException{

    public AbstractException(String message) {
        super(message);
    }

    abstract public int getStatusCode();
    public String getMessage(){
        return super.getMessage();
    }

}
