package com.wex.purchase.transaction.wexpurchasetransaction.service;

import com.wex.purchase.transaction.wexpurchasetransaction.client.TreasuryReportingClient;
import com.wex.purchase.transaction.wexpurchasetransaction.dto.TreasuryReportingResponseData;
import com.wex.purchase.transaction.wexpurchasetransaction.dto.TreasuryReportingResponseDto;
import com.wex.purchase.transaction.wexpurchasetransaction.model.PurchaseTransaction;
import com.wex.purchase.transaction.wexpurchasetransaction.repository.WexPurchaseTransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class WexPurchaseTransactionServiceTest {
    AutoCloseable autoCloseable;
    WexPurchaseTransactionRepository wexPurchaseTransactionRepository = mock(WexPurchaseTransactionRepository.class);
    TreasuryReportingClient treasuryReportingClient = mock(TreasuryReportingClient.class);

    WexPurchaseTransactionService wexPurchaseTransactionService = spy(
            new WexPurchaseTransactionService(
                    this.wexPurchaseTransactionRepository,
                    this.treasuryReportingClient
            )
    );

    @BeforeEach
    void setUp() {
        this.autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach()
    void tearDown() throws Exception {
        this.autoCloseable.close();
    }

    @Test
    void testX() {
        PurchaseTransaction purchaseTransaction = new PurchaseTransaction();
        purchaseTransaction.setId(1L);
        purchaseTransaction.setDescription("desc");
        purchaseTransaction.setTransactionDate(LocalDate.now());
        purchaseTransaction.setAmount(BigDecimal.TEN);

        TreasuryReportingResponseDto treasuryReportingResponseDto = new TreasuryReportingResponseDto();
        TreasuryReportingResponseData treasuryReportingResponseData = new TreasuryReportingResponseData();
        treasuryReportingResponseData.setExchangeRate(BigDecimal.ZERO);
        treasuryReportingResponseDto.setData(Collections.singletonList(treasuryReportingResponseData));

        doReturn(Optional.of(purchaseTransaction))
                .when(this.wexPurchaseTransactionRepository)
                .findById(anyLong());

        doReturn(treasuryReportingResponseDto)
                .when(this.treasuryReportingClient)
                .callForExchangeRate(anyString(), any(LocalDate.class));

        this.wexPurchaseTransactionService.retrievePurchaseTransaction(1L, "Brazil");

        verify(this.wexPurchaseTransactionRepository, times(1))
                .findById(anyLong());

        verify(this.treasuryReportingClient, times(1))
                .callForExchangeRate(anyString(), any(LocalDate.class));
    }
}
