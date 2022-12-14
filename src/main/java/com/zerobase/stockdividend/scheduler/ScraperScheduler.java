package com.zerobase.stockdividend.scheduler;

import com.zerobase.stockdividend.model.CompanyDto;
import com.zerobase.stockdividend.model.ScrappedResult;
import com.zerobase.stockdividend.model.constants.CacheKey;
import com.zerobase.stockdividend.persist.CompanyRepository;
import com.zerobase.stockdividend.persist.DividendRepository;
import com.zerobase.stockdividend.persist.entity.Company;
import com.zerobase.stockdividend.persist.entity.Dividend;
import com.zerobase.stockdividend.scrapper.Scrapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableCaching
@RequiredArgsConstructor
public class ScraperScheduler {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    private final Scrapper yahooFinanceScrapper;

    @CacheEvict(value = CacheKey.KEY_FINANCE, allEntries = true)
    @Scheduled(cron = "${scheduler.scrap.yahoo}")
    public void yahooFinanceScheduling() {
//        log.info("scrapping scheduler is started");
        // 저장된 회사 목록을 조회
        List<Company> companies = companyRepository.findAll();

        // 회사마다 배당금 정보를 새로 스크래핑
        for (var company : companies) {
            log.info("scrapping scheduler is started -> " + company.getName());
            ScrappedResult scrappedResult = yahooFinanceScrapper.scrap(CompanyDto.builder()
                .name(company.getName())
                .ticker(company.getTicker())
                .build());

            // 스크래핑한 배당금 정보 중 DB에 없는 값은 저장
            scrappedResult.getDividendList().stream()
                // 디비든 모델을 디비든 엔티티로 매핑
                .map(e -> Dividend.from(e, company.getId()))
                // 엘리먼트를 하나씩 디비든 레파지토리에 삽입
                .forEach(e -> {
                    boolean exists = dividendRepository.existsByCompanyIdAndDate(e.getCompanyId(), e.getDate());
                    if(!exists){
                        dividendRepository.save(e);
                        log.info("insert new dividend -> " + e);
                    }
                });
            // 연속적으로 스크래핑 대상 사이트 서버에 요청을 날리지 않도록 일시정지
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
