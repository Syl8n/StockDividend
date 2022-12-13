package com.zerobase.stockdividend.persist.entity;

import com.zerobase.stockdividend.model.DividendDto;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"companyId", "date"}
        )
    }
)
public class Dividend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;

    private LocalDate date;

    private String dividend;

    public static Dividend from(DividendDto dividendDto, Long companyId){
        return Dividend.builder()
            .companyId(companyId)
            .date(dividendDto.getDate())
            .dividend(dividendDto.getDividend())
            .build();
    }
}
