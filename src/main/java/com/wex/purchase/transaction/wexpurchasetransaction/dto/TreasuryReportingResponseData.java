package com.wex.purchase.transaction.wexpurchasetransaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class TreasuryReportingResponseData {
    @JsonProperty("exchange_rate")
    private BigDecimal exchangeRate;
}
