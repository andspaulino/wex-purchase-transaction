package com.wex.purchase.transaction.wexpurchasetransaction.service;

import com.wex.purchase.transaction.wexpurchasetransaction.client.TreasuryReportingClient;
import com.wex.purchase.transaction.wexpurchasetransaction.dto.PurchaseTransactionDto;
import com.wex.purchase.transaction.wexpurchasetransaction.dto.RetrievedPurchaseTransactionDto;
import com.wex.purchase.transaction.wexpurchasetransaction.dto.TreasuryReportingResponseDto;
import com.wex.purchase.transaction.wexpurchasetransaction.model.PurchaseTransaction;
import com.wex.purchase.transaction.wexpurchasetransaction.repository.WexPurchaseTransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class WexPurchaseTransactionService {
    private final WexPurchaseTransactionRepository wexPurchaseTransactionRepository;
    private final TreasuryReportingClient treasuryReportingClient;

    public WexPurchaseTransactionService(
            WexPurchaseTransactionRepository wexPurchaseTransactionRepository,
            TreasuryReportingClient treasuryReportingClient
    ) {
        this.wexPurchaseTransactionRepository = wexPurchaseTransactionRepository;
        this.treasuryReportingClient = treasuryReportingClient;
    }

    public void insertPurchaseTransaction(PurchaseTransactionDto purchaseTransactionDto) {
        PurchaseTransaction transaction = new PurchaseTransaction();
        transaction.setAmount(purchaseTransactionDto.getAmount());
        transaction.setDescription(purchaseTransactionDto.getDescription());
        transaction.setTransactionDate(purchaseTransactionDto.getTransactionDate());

        this.wexPurchaseTransactionRepository.save(transaction);
    }

    public RetrievedPurchaseTransactionDto retrievePurchaseTransaction(Long id, String country) {

        PurchaseTransaction purchaseTransaction = this.wexPurchaseTransactionRepository.findById(id).get();

        ResponseEntity<TreasuryReportingResponseDto> treasuryReportingResponseDtoResponseEntity =
                this.treasuryReportingClient.callForExchangeRate(country, purchaseTransaction.getTransactionDate());

        if(!treasuryReportingResponseDtoResponseEntity.hasBody()
                || Objects.isNull(treasuryReportingResponseDtoResponseEntity.getBody())
                || treasuryReportingResponseDtoResponseEntity.getBody().getData().isEmpty()) {
            throw new NoSuchElementException();
        }

        return new RetrievedPurchaseTransactionDto(
                purchaseTransaction.getId(),
                purchaseTransaction.getDescription(),
                purchaseTransaction.getTransactionDate(),
                purchaseTransaction.getAmount(),
                treasuryReportingResponseDtoResponseEntity.getBody().getData().get(0).getExchangeRate(),
                purchaseTransaction.getAmount()
                        .multiply(
                                treasuryReportingResponseDtoResponseEntity.getBody().getData().get(0).getExchangeRate()
                        )
                        .setScale(2, RoundingMode.HALF_EVEN)
        );
    }
}
