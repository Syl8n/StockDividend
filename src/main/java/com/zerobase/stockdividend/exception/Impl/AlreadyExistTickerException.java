package com.zerobase.stockdividend.exception.Impl;

import com.zerobase.stockdividend.exception.AbstractException;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class AlreadyExistTickerException extends AbstractException {

    public AlreadyExistTickerException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "이미 추가된 ticker입니다 -> " + super.getMessage();
    }
}
