package com.wex.purchase.transaction.wexpurchasetransaction.controller;

import com.wex.purchase.transaction.wexpurchasetransaction.dto.PurchaseTransactionDto;
import com.wex.purchase.transaction.wexpurchasetransaction.dto.RetrievedPurchaseTransactionDto;
import com.wex.purchase.transaction.wexpurchasetransaction.service.PurchaseTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/purchase-transactions")
@Tag(name = "Purchase Transaction", description = "APIs for integrating purchase transactions")
public class PurchaseTransactionController {
    private final PurchaseTransactionService purchaseTransactionService;

    public PurchaseTransactionController(
            PurchaseTransactionService purchaseTransactionService
    ) {
        this.purchaseTransactionService = purchaseTransactionService;
    }

    @PostMapping("/store")
    @Operation(summary = "Store a purchase transaction")
    public ResponseEntity<Void> storePurchaseTransaction(
            @Valid @RequestBody PurchaseTransactionDto purchaseTransactionDto
    ) {
        this.purchaseTransactionService.insertPurchaseTransaction(purchaseTransactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/retrieve/{id}/{country}")
    @Operation(summary = "Retrieve a purchase transaction")
    public ResponseEntity<RetrievedPurchaseTransactionDto> retrievePurchaseTransaction(
            @PathVariable(name = "id") Long id,
            @PathVariable(name = "country") String country
    ) {
        return this.purchaseTransactionService.retrievePurchaseTransaction(id, country)
                .map((RetrievedPurchaseTransactionDto retrievedPurchaseTransactionDto) ->
                    ResponseEntity
                            .status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(retrievedPurchaseTransactionDto)
                )
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


}
