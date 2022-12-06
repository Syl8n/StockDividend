package com.zerobase.stockdividend.model;

import com.zerobase.stockdividend.persist.entity.Dividend;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DividendDto {
    private LocalDate date;
    private String dividend;

    public static DividendDto from(Dividend dividend){
        return DividendDto.builder()
            .date(dividend.getDate())
            .dividend(dividend.getDividend())
            .build();
    }
}
