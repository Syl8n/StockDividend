package com.zerobase.stockdividend.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;
    private String dividend;

    public static DividendDto from(Dividend dividend){
        return DividendDto.builder()
            .date(dividend.getDate())
            .dividend(dividend.getDividend())
            .build();
    }
}
