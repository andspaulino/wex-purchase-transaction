package com.wex.purchase.transaction.wexpurchasetransaction.repository;

import com.wex.purchase.transaction.wexpurchasetransaction.model.PurchaseTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseTransactionRepository extends JpaRepository<PurchaseTransaction, Long> {}