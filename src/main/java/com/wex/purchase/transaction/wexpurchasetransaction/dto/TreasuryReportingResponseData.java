package com.wex.purchase.transaction.wexpurchasetransaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TreasuryReportingResponseData {

    @JsonProperty("exchange_rate")
    private BigDecimal exchangeRate;

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
