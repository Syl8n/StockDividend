package com.zerobase.stockdividend.persist.entity;

import com.zerobase.stockdividend.model.CompanyDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String ticker;

    private String name;

    public static Company from(CompanyDto companyDto){
        return Company.builder()
            .name(companyDto.getName())
            .ticker(companyDto.getTicker())
            .build();
    }
}
