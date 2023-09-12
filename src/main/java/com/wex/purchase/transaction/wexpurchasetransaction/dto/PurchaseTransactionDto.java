package com.wex.purchase.transaction.wexpurchasetransaction.dto;

import com.wex.purchase.transaction.wexpurchasetransaction.model.PurchaseTransaction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * DTO for {@link PurchaseTransaction}
 */
public class PurchaseTransactionDto implements Serializable {
    private final BigDecimal amount;
    private final String description;

    public PurchaseTransactionDto(BigDecimal amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseTransactionDto entity = (PurchaseTransactionDto) o;
        return Objects.equals(this.amount, entity.amount) &&
                Objects.equals(this.description, entity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, description);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "amount = " + amount + ", " +
                "description = " + description + ")";
    }
}