package com.wex.purchase.transaction.wexpurchasetransaction.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.info.BuildProperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class OpenAPIConfigTest {
    AutoCloseable autoCloseable;
    BuildProperties buildProperties = mock(BuildProperties.class);
    OpenAPIConfig openAPIConfig;

    @BeforeEach
    void setUp() {
        this.autoCloseable = MockitoAnnotations.openMocks(this);
        this.openAPIConfig = new OpenAPIConfig(this.buildProperties);
    }

    @AfterEach()
    void tearDown() throws Exception {
        this.autoCloseable.close();
    }

    @Test
    void testMyOpenApi() {

        doReturn("1.0.0")
                .when(this.buildProperties)
                .getVersion();

        OpenAPI openAPI = this.openAPIConfig.myOpenApi();

        assertEquals("wex-purchase-transaction", openAPI.getInfo().getTitle());
        assertEquals("1.0.0", openAPI.getInfo().getVersion());
    }
}
