package com.wex.purchase.transaction.wexpurchasetransaction.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder(
        builderClassName = "Builder",
        builderMethodName = "Builder",
        toBuilder = true
)
@Getter
public class RetrievedPurchaseTransactionDto implements Serializable {
    private Long id;
    private String description;
    private LocalDate transactionDate;
    private BigDecimal originalAmount;
    private BigDecimal exchangeRate;
    private BigDecimal convertedAmount;
}
