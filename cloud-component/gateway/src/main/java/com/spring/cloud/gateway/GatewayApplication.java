package com.spring.cloud.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName GatewayApplication
 * @Description 网关服务启动类
 * @Author zgw
 * @Date 2020/1/9 16:20
 **/
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@RefreshScope
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Value("${test.value}")
    private String testValue;

    @RequestMapping("/hello")
    public String helloWorld() {
        return testValue;
    }
}
