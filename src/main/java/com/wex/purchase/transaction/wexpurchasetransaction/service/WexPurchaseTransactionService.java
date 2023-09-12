package com.wex.purchase.transaction.wexpurchasetransaction.service;

import com.wex.purchase.transaction.wexpurchasetransaction.dto.PurchaseTransactionDto;
import com.wex.purchase.transaction.wexpurchasetransaction.dto.RetrievedPurchaseTransactionDto;
import com.wex.purchase.transaction.wexpurchasetransaction.model.PurchaseTransaction;
import com.wex.purchase.transaction.wexpurchasetransaction.repository.WexPurchaseTransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WexPurchaseTransactionService {
    private final WexPurchaseTransactionRepository wexPurchaseTransactionRepository;

    public WexPurchaseTransactionService(
            WexPurchaseTransactionRepository wexPurchaseTransactionRepository
    ) {
        this.wexPurchaseTransactionRepository = wexPurchaseTransactionRepository;
    }

    public void insertPurchaseTransaction(PurchaseTransactionDto purchaseTransactionDto) {
        PurchaseTransaction transaction = new PurchaseTransaction();
        transaction.setAmount(purchaseTransactionDto.getAmount());
        transaction.setDescription(purchaseTransactionDto.getDescription());
        transaction.setTransactionDate(purchaseTransactionDto.getTransactionDate());

        this.wexPurchaseTransactionRepository.save(transaction);
    }

    public List<RetrievedPurchaseTransactionDto> retrievePurchaseTransaction(String country) {
        return this.wexPurchaseTransactionRepository.findAll()
                .stream()
                .map((PurchaseTransaction purchaseTransaction) -> {
                    return new RetrievedPurchaseTransactionDto(
                            purchaseTransaction.getId(),
                            purchaseTransaction.getDescription(),
                            purchaseTransaction.getTransactionDate(),
                            purchaseTransaction.getAmount(),
                            BigDecimal.ZERO,
                            BigDecimal.ZERO
                    );
                })
                .collect(Collectors.toList());
    }
}
