package com.wex.purchase.transaction.wexpurchasetransaction.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class PurchaseTransactionDto implements Serializable {
    @NotNull(message = "amount should not be null.")
    private final BigDecimal amount;
    @Size(max = 50, message = "description max length should be 50 characters.")
    private final String description;
    @NotNull(message = "transactionDate should not be null.")
    @PastOrPresent(message = "transactionDate should be in tha past or present.")
    private final LocalDate transactionDate;

    public PurchaseTransactionDto(BigDecimal amount, String description, LocalDate transactionDate) {
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseTransactionDto entity = (PurchaseTransactionDto) o;
        return Objects.equals(this.amount, entity.amount) &&
                Objects.equals(this.description, entity.description) &&
                Objects.equals(this.transactionDate, entity.transactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, description, transactionDate);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "amount = " + amount + ", " +
                "description = " + description + ", " +
                "transactionDate = " + transactionDate + ")";
    }
}