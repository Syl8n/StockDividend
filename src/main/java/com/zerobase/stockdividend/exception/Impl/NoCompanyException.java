package com.zerobase.stockdividend.exception.Impl;

import com.zerobase.stockdividend.exception.AbstractException;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class NoCompanyException extends AbstractException {

    public NoCompanyException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "존재하지 않는 회사명입니다 -> " + super.getMessage();
    }
}
