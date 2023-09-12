package com.wex.purchase.transaction.wexpurchasetransaction.controller;

import com.wex.purchase.transaction.wexpurchasetransaction.dto.PurchaseTransactionDto;
import com.wex.purchase.transaction.wexpurchasetransaction.dto.RetrievedPurchaseTransactionDto;
import com.wex.purchase.transaction.wexpurchasetransaction.service.WexPurchaseTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @PostMapping("store-purchase-transaction/")
    public ResponseEntity<Void> storePurchaseTransaction(
            @Valid @RequestBody PurchaseTransactionDto purchaseTransactionDto
    ) {
        this.wexPurchaseTransactionService.insertPurchaseTransaction(purchaseTransactionDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("retrieve-purchase-transaction/")
    public ResponseEntity<List<RetrievedPurchaseTransactionDto>> retrievePurchaseTransaction(
            @RequestParam(name = "country", required = false) String country
    ) {
        List<RetrievedPurchaseTransactionDto> purchaseTransactions =
                this.wexPurchaseTransactionService.retrievePurchaseTransaction(country);
        return ResponseEntity.status(HttpStatus.OK).body(purchaseTransactions);
    }


}
