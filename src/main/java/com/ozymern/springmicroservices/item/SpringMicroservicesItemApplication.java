package com.ozymern.springmicroservices.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//un solo cliente con feing, si tuvieramos mas se ocuparia @RibbonClients
//@RibbonClient(name="product")
@EnableFeignClients
@SpringBootApplication
@EnableEurekaClient
//habilitamos Hystrix
@EnableCircuitBreaker
//excluir datasource, para que no sea necesario tener h2 u otram base de datos
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class SpringMicroservicesItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMicroservicesItemApplication.class, args);
    }

}
