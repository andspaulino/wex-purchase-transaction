package com.wex.purchase.transaction.wexpurchasetransaction.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    private final BuildProperties buildProperties;

    public OpenAPIConfig(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public OpenAPI myOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("wex-purchase-transaction")
                                .version(this.buildProperties.getVersion())
                );
    }
}
