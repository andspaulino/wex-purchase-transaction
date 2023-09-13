package com.wex.purchase.transaction.wexpurchasetransaction.client;

import com.wex.purchase.transaction.wexpurchasetransaction.dto.TreasuryReportingResponseData;
import com.wex.purchase.transaction.wexpurchasetransaction.dto.TreasuryReportingResponseDto;
import com.wex.purchase.transaction.wexpurchasetransaction.exception.TreasuryReportingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TreasuryReportingClientTest {
    AutoCloseable autoCloseable;
    RestTemplate restTemplate = mock(RestTemplate.class);
    TreasuryReportingClient treasuryReportingClient = spy(new TreasuryReportingClient(this.restTemplate));

    @BeforeEach
    void setUp() {
        this.autoCloseable = MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(this.treasuryReportingClient, "url", "https://url.com");
    }

    @AfterEach()
    void tearDown() throws Exception {
        this.autoCloseable.close();
    }

    @Test
    @DisplayName("This method should return a ExchangeRate successfully.")
    void testCallForExchangeRate0() {
        TreasuryReportingResponseDto treasuryReportingResponseDto = new TreasuryReportingResponseDto();
        TreasuryReportingResponseData treasuryReportingResponseData = new TreasuryReportingResponseData();
        treasuryReportingResponseData.setExchangeRate(BigDecimal.ZERO);
        treasuryReportingResponseDto.setData(Collections.singletonList(treasuryReportingResponseData));

        doReturn(ResponseEntity.status(HttpStatus.OK).body(treasuryReportingResponseDto))
                .when(this.restTemplate)
                .exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), eq(TreasuryReportingResponseDto.class));

        this.treasuryReportingClient.callForExchangeRate("Brazil", LocalDate.now());

        verify(this.restTemplate, times(1))
                .exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), eq(TreasuryReportingResponseDto.class));
    }

    @Test
    @DisplayName("This method should return a ExchangeRate successfully.")
    void testCallForExchangeRate1() {
        doReturn(ResponseEntity.status(HttpStatus.OK).build())
                .when(this.restTemplate)
                .exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), eq(TreasuryReportingResponseDto.class));

        assertThrows(
                TreasuryReportingException.class,
                () -> this.treasuryReportingClient.callForExchangeRate("Brazil", LocalDate.now())
        );

        verify(this.restTemplate, times(1))
                .exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), eq(TreasuryReportingResponseDto.class));
    }

    @Test
    @DisplayName("This method should return a ExchangeRate successfully.")
    void testCallForExchangeRate2() {
        TreasuryReportingResponseDto treasuryReportingResponseDto = new TreasuryReportingResponseDto();
        TreasuryReportingResponseData treasuryReportingResponseData = new TreasuryReportingResponseData();
        treasuryReportingResponseData.setExchangeRate(BigDecimal.ZERO);

        doReturn(ResponseEntity.status(HttpStatus.OK).body(treasuryReportingResponseDto))
                .when(this.restTemplate)
                .exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), eq(TreasuryReportingResponseDto.class));

        assertThrows(
                TreasuryReportingException.class,
                () -> this.treasuryReportingClient.callForExchangeRate("Brazil", LocalDate.now())
        );

        verify(this.restTemplate, times(1))
                .exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), eq(TreasuryReportingResponseDto.class));
    }

    @Test
    @DisplayName("This method should return a ExchangeRate successfully.")
    void testCallForExchangeRate3() {
        TreasuryReportingResponseDto treasuryReportingResponseDto = new TreasuryReportingResponseDto();
        TreasuryReportingResponseData treasuryReportingResponseData = new TreasuryReportingResponseData();
        treasuryReportingResponseData.setExchangeRate(BigDecimal.ZERO);
        treasuryReportingResponseDto.setData(Collections.emptyList());

        doReturn(ResponseEntity.status(HttpStatus.OK).body(treasuryReportingResponseDto))
                .when(this.restTemplate)
                .exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), eq(TreasuryReportingResponseDto.class));

        assertThrows(
                TreasuryReportingException.class,
                () -> this.treasuryReportingClient.callForExchangeRate("Brazil", LocalDate.now())
        );

        verify(this.restTemplate, times(1))
                .exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), eq(TreasuryReportingResponseDto.class));
    }

    @Test
    @DisplayName("This method should return a ExchangeRate successfully.")
    void testCallForExchangeRate4() {
        HttpStatusCodeException httpClientErrorException = new HttpClientErrorException(HttpStatus.BAD_REQUEST);

        doThrow(httpClientErrorException)
                .when(this.restTemplate)
                .exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), eq(TreasuryReportingResponseDto.class));

        assertThrows(
                TreasuryReportingException.class,
                () -> this.treasuryReportingClient.callForExchangeRate("Brazil", LocalDate.now())
        );

        verify(this.restTemplate, times(1))
                .exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), eq(TreasuryReportingResponseDto.class));
    }

}
