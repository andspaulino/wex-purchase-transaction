package com.wex.purchase.transaction.wexpurchasetransaction.controller;

import com.wex.purchase.transaction.wexpurchasetransaction.dto.PurchaseTransactionDto;
import com.wex.purchase.transaction.wexpurchasetransaction.dto.RetrievedPurchaseTransactionDto;
import com.wex.purchase.transaction.wexpurchasetransaction.service.WexPurchaseTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    @GetMapping("retrieve-purchase-transaction/{id}")
    public ResponseEntity<RetrievedPurchaseTransactionDto> retrievePurchaseTransaction(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "country", required = false) String country
    ) {
        return this.wexPurchaseTransactionService.retrievePurchaseTransaction(id, country)
                .map((RetrievedPurchaseTransactionDto retrievedPurchaseTransactionDto) ->
                    ResponseEntity
                            .status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(retrievedPurchaseTransactionDto)
                )
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


}
