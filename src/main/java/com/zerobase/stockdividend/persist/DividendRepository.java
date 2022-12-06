package com.zerobase.stockdividend.persist;

import com.zerobase.stockdividend.persist.entity.Dividend;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DividendRepository extends JpaRepository<Dividend, Long> {
    List<Dividend> findAllByCompanyId(Long companyId);
}
