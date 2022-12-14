package com.zerobase.stockdividend.exception.Impl;

import com.zerobase.stockdividend.exception.AbstractException;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class AlreadyExistUserException extends AbstractException {

    public AlreadyExistUserException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "이미 존재하는 사용자명입니다 -> " + super.getMessage();
    }
}
