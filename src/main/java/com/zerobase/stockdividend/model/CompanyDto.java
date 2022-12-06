package com.zerobase.stockdividend.model;

import com.zerobase.stockdividend.persist.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
    private String ticker;
    private String name;

    public static CompanyDto from(Company company){
        return CompanyDto.builder()
            .ticker(company.getTicker())
            .name(company.getName())
            .build();
    }
}
