package com.wex.purchase.transaction.wexpurchasetransaction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ToString
public class PurchaseTransactionDto implements Serializable {
    @NotNull(message = "should not be null.")
    @DecimalMin(value = "0.0", inclusive = false, message = "value must be higher than 0.0")
    @Digits(integer = 15, fraction = 3, message = "must have a maximum of 15 integer and maximum of 3 decimal digits.")
    private final BigDecimal amount;
    @Size(max = 50, message = "max length should be 50 characters.")
    private final String description;
    @NotNull(message = "should not be null.")
    @PastOrPresent(message = "should be in the past or present.")
    private final LocalDate transactionDate;
}
