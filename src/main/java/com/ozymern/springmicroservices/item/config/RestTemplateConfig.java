package com.ozymern.springmicroservices.item.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    //para el balanceo de carga con ribbon
    @LoadBalanced
    public RestTemplate registerTemplate(){

        return new RestTemplate();

    }

}
