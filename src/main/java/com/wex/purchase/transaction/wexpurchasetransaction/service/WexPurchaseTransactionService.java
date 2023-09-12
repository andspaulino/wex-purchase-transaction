package com.wex.purchase.transaction.wexpurchasetransaction.service;

import com.wex.purchase.transaction.wexpurchasetransaction.model.PurchaseTransaction;
import com.wex.purchase.transaction.wexpurchasetransaction.repository.WexPurchaseTransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WexPurchaseTransactionService {
    private final WexPurchaseTransactionRepository wexPurchaseTransactionRepository;

    public WexPurchaseTransactionService(
            WexPurchaseTransactionRepository wexPurchaseTransactionRepository
    ) {
        this.wexPurchaseTransactionRepository = wexPurchaseTransactionRepository;
    }

    public List<PurchaseTransaction> findAll(){
        return this.wexPurchaseTransactionRepository.findAll();
    }

    public void insert() {
        PurchaseTransaction transaction = new PurchaseTransaction();
        transaction.setAmount(BigDecimal.valueOf(200.50));
        transaction.setDescription("teste");

        this.wexPurchaseTransactionRepository.save(transaction);
    }
}
