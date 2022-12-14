package com.zerobase.stockdividend.exception.Impl;

import com.zerobase.stockdividend.exception.AbstractException;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class NotExistTickerException extends AbstractException {

    public NotExistTickerException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "존재하지 않거나 잘못된 ticker입니다 -> " + super.getMessage();
    }
}
