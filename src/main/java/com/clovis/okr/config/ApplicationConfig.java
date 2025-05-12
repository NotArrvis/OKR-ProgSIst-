package com.clovis.okr.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {

  @Bean
  @Scope(BeanDefinition.SCOPE_PROTOTYPE)
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
