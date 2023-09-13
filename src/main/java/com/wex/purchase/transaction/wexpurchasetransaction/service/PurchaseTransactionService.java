package com.wex.purchase.transaction.wexpurchasetransaction.service;

import com.wex.purchase.transaction.wexpurchasetransaction.client.TreasuryReportingClient;
import com.wex.purchase.transaction.wexpurchasetransaction.dto.PurchaseTransactionDto;
import com.wex.purchase.transaction.wexpurchasetransaction.dto.RetrievedPurchaseTransactionDto;
import com.wex.purchase.transaction.wexpurchasetransaction.dto.TreasuryReportingResponseData;
import com.wex.purchase.transaction.wexpurchasetransaction.exception.TreasuryReportingException;
import com.wex.purchase.transaction.wexpurchasetransaction.model.PurchaseTransaction;
import com.wex.purchase.transaction.wexpurchasetransaction.repository.PurchaseTransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PurchaseTransactionService {
    private final PurchaseTransactionRepository purchaseTransactionRepository;
    private final TreasuryReportingClient treasuryReportingClient;

    public PurchaseTransactionService(
            PurchaseTransactionRepository purchaseTransactionRepository,
            TreasuryReportingClient treasuryReportingClient
    ) {
        this.purchaseTransactionRepository = purchaseTransactionRepository;
        this.treasuryReportingClient = treasuryReportingClient;
    }

    public void insertPurchaseTransaction(PurchaseTransactionDto purchaseTransactionDto) {
        PurchaseTransaction transaction = new PurchaseTransaction();
        transaction.setAmount(purchaseTransactionDto.getAmount());
        transaction.setDescription(purchaseTransactionDto.getDescription());
        transaction.setTransactionDate(purchaseTransactionDto.getTransactionDate());

        this.purchaseTransactionRepository.save(transaction);
    }

    public Optional<RetrievedPurchaseTransactionDto> retrievePurchaseTransaction(Long id, String country) {
        return this.purchaseTransactionRepository.findById(id)
                .map((PurchaseTransaction purchaseTransaction) ->
                        RetrievedPurchaseTransactionDto.Builder()
                                .id(purchaseTransaction.getId())
                                .description(purchaseTransaction.getDescription())
                                .transactionDate(purchaseTransaction.getTransactionDate())
                                .originalAmount(purchaseTransaction.getAmount())
                                .build()
                )
                .map((RetrievedPurchaseTransactionDto retrievedPurchaseTransactionDto) -> {

                    TreasuryReportingResponseData treasuryReportingResponseDto =
                            this.treasuryReportingClient
                                    .callForExchangeRate(country, retrievedPurchaseTransactionDto.getTransactionDate())
                                    .getData()
                                    .stream()
                                    .findFirst()
                                    .orElseThrow(() ->
                                            new TreasuryReportingException(
                                                    "Error occurred while trying to retrieve exchange rate",
                                                    new NoSuchElementException()
                                            )
                                    );

                    return retrievedPurchaseTransactionDto.toBuilder()
                            .exchangeRate(treasuryReportingResponseDto.getExchangeRate())
                            .build();
                })
                .map((RetrievedPurchaseTransactionDto retrievedPurchaseTransactionDto) -> {
                    BigDecimal originalAmount = retrievedPurchaseTransactionDto.getOriginalAmount();
                    BigDecimal exchangeRate = retrievedPurchaseTransactionDto.getExchangeRate();
                    BigDecimal convertedAmount =
                            originalAmount.multiply(exchangeRate).setScale(2, RoundingMode.HALF_EVEN);
                    return retrievedPurchaseTransactionDto.toBuilder()
                            .convertedAmount(convertedAmount)
                            .build();
                });
    }
}
