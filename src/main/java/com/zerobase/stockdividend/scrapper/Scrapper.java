package com.zerobase.stockdividend.scrapper;

import com.zerobase.stockdividend.model.CompanyDto;
import com.zerobase.stockdividend.model.ScrappedResult;

public interface Scrapper {
    CompanyDto scrapCompanyByTicker(String ticker);
    ScrappedResult scrap(CompanyDto company);
}
