package com.wex.purchase.transaction.wexpurchasetransaction.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class RetrievedPurchaseTransactionDto implements Serializable {
    private final Long id;
    private final String description;
    private final LocalDate transactionDate;
    private final BigDecimal originalAmount;
    private final BigDecimal exchangeRate;
    private final BigDecimal convertedAmount;

    public RetrievedPurchaseTransactionDto(Long id, String description, LocalDate transactionDate, BigDecimal originalAmount, BigDecimal exchangeRate, BigDecimal convertedAmount) {
        this.id = id;
        this.description = description;
        this.transactionDate = transactionDate;
        this.originalAmount = originalAmount;
        this.exchangeRate = exchangeRate;
        this.convertedAmount = convertedAmount;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }
}
