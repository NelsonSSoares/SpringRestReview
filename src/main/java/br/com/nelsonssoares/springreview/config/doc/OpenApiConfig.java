package br.com.nelsonssoares.springreview.config.doc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("REST API's RESTFul from 0 with Java, Spring boot, kubernets and Docker")
                        .version("v1")
                        .description("REST API's RESTFul from 0 with Java, Spring boot, kubernets and Docker")
                        .termsOfService("https://github.com/NelsonSSoares")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://github.com/NelsonSSoares")
                        )
                );
    }
}
