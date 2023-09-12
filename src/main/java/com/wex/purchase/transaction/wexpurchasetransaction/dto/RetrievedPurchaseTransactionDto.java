package com.wex.purchase.transaction.wexpurchasetransaction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class RetrievedPurchaseTransactionDto implements Serializable {
    private final Long id;
    private final String description;
    private final LocalDate transactionDate;
    private final BigDecimal originalAmount;
    private final BigDecimal exchangeRate;
    private final BigDecimal convertedAmount;
}
