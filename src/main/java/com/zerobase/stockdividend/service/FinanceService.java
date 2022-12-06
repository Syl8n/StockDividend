package com.zerobase.stockdividend.service;

import com.zerobase.stockdividend.model.CompanyDto;
import com.zerobase.stockdividend.model.DividendDto;
import com.zerobase.stockdividend.model.ScrappedResult;
import com.zerobase.stockdividend.persist.CompanyRepository;
import com.zerobase.stockdividend.persist.DividendRepository;
import com.zerobase.stockdividend.persist.entity.Company;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public ScrappedResult getDividendByCompanyName(String companyName){
        // 회사명으로 회사 정보를 조회
        Company company = companyRepository.findByName(companyName)
            .orElseThrow(() -> new RuntimeException("Company not exists"));

        // 회사 ID를 기준으로 배당금 정보 조회
        List<DividendDto> dividends = dividendRepository.findAllByCompanyId(company.getId())
            .stream()
            .map(DividendDto::from)
            .collect(Collectors.toList());

        // 결과 반환
        return new ScrappedResult(CompanyDto.from(company), dividends);
    }
}
