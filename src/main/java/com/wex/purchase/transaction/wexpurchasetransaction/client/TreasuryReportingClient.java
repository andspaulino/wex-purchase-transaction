package com.wex.purchase.transaction.wexpurchasetransaction.client;

import com.wex.purchase.transaction.wexpurchasetransaction.dto.TreasuryReportingResponseDto;
import com.wex.purchase.transaction.wexpurchasetransaction.exception.TreasuryReportingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Objects;

@Component
public class TreasuryReportingClient {
    @Value("${treasury.rates.of.exchange.url}")
    private String url;
    private final RestTemplate restTemplate;

    public TreasuryReportingClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TreasuryReportingResponseDto callForExchangeRate(String country, LocalDate date) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> objectHttpEntity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder.fromUriString(this.url)
                .queryParam("fields", "record_date, exchange_rate")
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

        ResponseEntity<TreasuryReportingResponseDto> exchange = null;

        try {
            exchange = this.restTemplate
                    .exchange(uri, HttpMethod.GET, objectHttpEntity, TreasuryReportingResponseDto.class);
        } catch (HttpStatusCodeException e) {
            throw new TreasuryReportingException(
                    "Error occurred while trying to call for exchange rate",
                    e
            );
        }

        TreasuryReportingResponseDto body = exchange.getBody();

        if (Objects.isNull(body)) {
            throw new TreasuryReportingException("No body has returned", new NoSuchElementException());
        }

        if(Objects.isNull(body.getData()) || body.getData().isEmpty()) {
            throw new TreasuryReportingException("No data has returned", new NoSuchElementException());
        }

        return body;
    }

}
