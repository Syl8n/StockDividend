package com.zerobase.stockdividend.web.persist.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
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
public class Dividend {
    @Id
    private Long id;

    private Long companyId;

    private LocalDateTime date;

    private String dividend;
}
