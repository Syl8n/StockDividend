package com.zerobase.stockdividend.persist;

import com.zerobase.stockdividend.persist.entity.Dividend;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DividendRepository extends JpaRepository<Dividend, Long> {
    List<Dividend> findAllByCompanyId(Long companyId);

    @Transactional
    void deleteAllByCompanyId(Long id);

    boolean existsByCompanyIdAndDate(Long companyId, LocalDate date);
}
