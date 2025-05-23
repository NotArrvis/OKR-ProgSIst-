package com.clovis.okr.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI okrOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("OKR Management API")
            .description("API for managing Objectives, Key Results, and Initiatives")
            .version("1.0"));
  }
}
