package com.wex.purchase.transaction.wexpurchasetransaction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wex.purchase.transaction.wexpurchasetransaction.client.TreasuryReportingClient;
import com.wex.purchase.transaction.wexpurchasetransaction.dto.PurchaseTransactionDto;
import com.wex.purchase.transaction.wexpurchasetransaction.dto.TreasuryReportingResponseData;
import com.wex.purchase.transaction.wexpurchasetransaction.dto.TreasuryReportingResponseDto;
import com.wex.purchase.transaction.wexpurchasetransaction.model.PurchaseTransaction;
import com.wex.purchase.transaction.wexpurchasetransaction.repository.PurchaseTransactionRepository;
import com.wex.purchase.transaction.wexpurchasetransaction.service.PurchaseTransactionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class PurchaseTransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PurchaseTransactionService purchaseTransactionService;

    @Autowired
    PurchaseTransactionRepository purchaseTransactionRepository;

    @MockBean
    TreasuryReportingClient treasuryReportingClient;

    @AfterEach
    void tearDown() {
        this.purchaseTransactionRepository.deleteAll();
    }

    @Test
    void testStorePurchaseTransaction0() throws Exception {

        PurchaseTransactionDto purchaseTransactionDto =
                new PurchaseTransactionDto(BigDecimal.TEN, "desc", LocalDate.now());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/purchase-transactions/store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(purchaseTransactionDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testStorePurchaseTransaction1() throws Exception {

        PurchaseTransactionDto purchaseTransactionDto =
                new PurchaseTransactionDto(BigDecimal.TEN, "desc", LocalDate.of(2032, 12, 12));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/purchase-transactions/store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(purchaseTransactionDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testRetrievePurchaseTransaction0() throws Exception {
        PurchaseTransaction purchaseTransaction = new PurchaseTransaction();
        purchaseTransaction.setDescription("desc");
        purchaseTransaction.setTransactionDate(LocalDate.now());
        purchaseTransaction.setAmount(BigDecimal.TEN);

        this.purchaseTransactionRepository.save(purchaseTransaction);

        TreasuryReportingResponseDto treasuryReportingResponseDto = new TreasuryReportingResponseDto();
        TreasuryReportingResponseData treasuryReportingResponseData = new TreasuryReportingResponseData();
        treasuryReportingResponseData.setExchangeRate(BigDecimal.ZERO);
        treasuryReportingResponseDto.setData(Collections.singletonList(treasuryReportingResponseData));

        when(this.treasuryReportingClient.callForExchangeRate(anyString(), any(LocalDate.class)))
                .thenReturn(treasuryReportingResponseDto);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/purchase-transactions/retrieve/{id}", 1L)
                        .param("country", "Brazil")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testRetrievePurchaseTransaction1() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/purchase-transactions/retrieve/{id}", 1L)
                        .param("country", "Brazil")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());;
    }

}
