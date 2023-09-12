package com.wex.purchase.transaction.wexpurchasetransaction.client;

import com.wex.purchase.transaction.wexpurchasetransaction.dto.TreasuryReportingResponseDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@Component
public class TreasuryReportingClient {
    private final String TREASURY_GOV_BASE_URL = "https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1/accounting/od/rates_of_exchange";
    private final RestTemplate restTemplate;

    public TreasuryReportingClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<TreasuryReportingResponseDto> callForExchangeRate(String country, LocalDate date) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> objectHttpEntity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder.fromUriString(TREASURY_GOV_BASE_URL)
                .queryParam("fields", "record_date,exchange_rate")
                .queryParam(
                        "filter",
                        String.format(
                                "country:eq:%s,record_date:gte:%s,record_date:lte:%s",
                                country,
                                date.minusMonths(6L),
                                date
                        )
                )
                .queryParam("sort", "-record_date")
                .queryParam("page[size]", "1")
                .build(false)
                .toUri();

        return this.restTemplate.exchange(uri, HttpMethod.GET, objectHttpEntity, TreasuryReportingResponseDto.class);
    }


}
