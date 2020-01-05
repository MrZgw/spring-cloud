package com.spring.cloud.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName CloudAlibabaRegistry
 * @Description nacos服务注册中心启动类
 * @Author zgw
 * @Date 2020/1/5 18:32
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class CloudAlibabaRegistry {

    public static void main(String[] args) {
        SpringApplication.run(CloudAlibabaRegistry.class, args);
    }
}
