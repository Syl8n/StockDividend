package com.zerobase.stockdividend.web.persist;

import com.zerobase.stockdividend.web.persist.entity.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DividendRepository extends JpaRepository<Dividend, Long> {

}
