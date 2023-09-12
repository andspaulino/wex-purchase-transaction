package com.wex.purchase.transaction.wexpurchasetransaction.controller;

import com.wex.purchase.transaction.wexpurchasetransaction.model.PurchaseTransaction;
import com.wex.purchase.transaction.wexpurchasetransaction.service.WexPurchaseTransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class WexPurchaseTransactionController {
    private final WexPurchaseTransactionService wexPurchaseTransactionService;

    public WexPurchaseTransactionController(
            WexPurchaseTransactionService wexPurchaseTransactionService
    ) {
        this.wexPurchaseTransactionService = wexPurchaseTransactionService;
    }

    @GetMapping("/all")
    public List<PurchaseTransaction> getAll() {
        return wexPurchaseTransactionService.findAll();
    }

    @GetMapping("/add")
    public void insert() {
        this.wexPurchaseTransactionService.insert();
    }

}
