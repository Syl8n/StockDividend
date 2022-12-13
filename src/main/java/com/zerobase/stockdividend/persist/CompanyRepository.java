package com.zerobase.stockdividend.persist;

import com.zerobase.stockdividend.persist.entity.Company;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByTicker(String ticker);
    Optional<Company> findByName(String name);
    Optional<Company> findByTicker(String ticker);

    Page<Company> findByNameStartingWithIgnoreCase(String s, Pageable pageable);
}
