package com.zerobase.stockdividend.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScrappedResult {
    private CompanyDto company;
    private List<DividendDto> dividendList;

    public ScrappedResult(){
        this.dividendList = new ArrayList<>();
    }
}
