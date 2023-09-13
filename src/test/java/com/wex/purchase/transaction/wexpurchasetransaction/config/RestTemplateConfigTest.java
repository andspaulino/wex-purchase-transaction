package com.wex.purchase.transaction.wexpurchasetransaction.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class RestTemplateConfigTest {
    AutoCloseable autoCloseable;
    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    RestTemplateConfig restTemplateConfig = spy(new RestTemplateConfig());

    @BeforeEach
    void setUp() {
        this.autoCloseable = MockitoAnnotations.openMocks(this);
        this.restTemplateConfig = new RestTemplateConfig();
    }

    @AfterEach()
    void tearDown() throws Exception {
        this.autoCloseable.close();
    }

    @Test
    void testRestTemplateBeanCreation() {
        RestTemplate restTemplateMock = mock(RestTemplate.class);

        doReturn(restTemplateMock).when(this.restTemplateBuilder).build();

        RestTemplate restTemplate = this.restTemplateConfig
                .restTemplate(this.restTemplateBuilder);

        assertNotNull(restTemplate);
    }

}
