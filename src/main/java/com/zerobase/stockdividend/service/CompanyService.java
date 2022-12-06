package com.zerobase.stockdividend.service;

import com.zerobase.stockdividend.model.CompanyDto;
import com.zerobase.stockdividend.model.ScrappedResult;
import com.zerobase.stockdividend.persist.CompanyRepository;
import com.zerobase.stockdividend.persist.DividendRepository;
import com.zerobase.stockdividend.persist.entity.Company;
import com.zerobase.stockdividend.persist.entity.Dividend;
import com.zerobase.stockdividend.scrapper.Scrapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final Scrapper scrapper;
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public CompanyDto save(String ticker){
        boolean exists = companyRepository.existsByTicker(ticker);
        if(exists){
            throw new RuntimeException("Ticker already exists -> " + ticker);
        }
        return storeCompanyAndDividend(ticker);
    }

    public Page<Company> getAllCompany(Pageable pageable){
        return companyRepository.findAll(pageable);
    }

    private CompanyDto storeCompanyAndDividend(String ticker){
        // 스크래핑
        CompanyDto companyDto = scrapper.scrapCompanyByTicker(ticker);
        if(ObjectUtils.isEmpty(companyDto)){
            throw new RuntimeException("failed to scrap ticker -> " + ticker);
        }

        // 회사가 있다면, 배당금 정보를 스크래핑
        ScrappedResult scrappedResult = scrapper.scrap(companyDto);

        // 결과 매핑
        Company company = companyRepository.save(Company.from(companyDto));
        List<Dividend> dividendList = scrappedResult.getDividendList().stream()
            .map(e -> Dividend.from(e, company.getId()))
            .collect(Collectors.toList());
        dividendRepository.saveAll(dividendList);

        return companyDto;
    }
}
