package com.zerobase.stockdividend.web.persist;

import com.zerobase.stockdividend.web.persist.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
