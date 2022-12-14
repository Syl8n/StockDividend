package com.zerobase.stockdividend.service;

import com.zerobase.stockdividend.exception.Impl.NoCompanyException;
import com.zerobase.stockdividend.model.CompanyDto;
import com.zerobase.stockdividend.model.DividendDto;
import com.zerobase.stockdividend.model.ScrappedResult;
import com.zerobase.stockdividend.model.constants.CacheKey;
import com.zerobase.stockdividend.persist.CompanyRepository;
import com.zerobase.stockdividend.persist.DividendRepository;
import com.zerobase.stockdividend.persist.entity.Company;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    // Redis 사용 판단 기준
    // 요청이 자주 들어오는가?
    // 자주 변경되는 데이터인가?
    @Cacheable(key = "#companyName", value = CacheKey.KEY_FINANCE)
    public ScrappedResult getDividendByCompanyName(String companyName){
        log.info("search company -> " + companyName);
        // 회사명으로 회사 정보를 조회
        Company company = companyRepository.findByName(companyName)
            .orElseThrow(() -> new NoCompanyException(companyName));

        // 회사 ID를 기준으로 배당금 정보 조회
        List<DividendDto> dividends = dividendRepository.findAllByCompanyId(company.getId())
            .stream()
            .map(DividendDto::from)
            .collect(Collectors.toList());

        // 결과 반환
        return new ScrappedResult(CompanyDto.from(company), dividends);
    }
}
