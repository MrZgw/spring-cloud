package com.spring.cloud.rbac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName RbacApplication
 * @Description rbac服务启动类
 * @Author zgw
 * @Date 2020/1/17 11:32
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class RbacApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacApplication.class, args);
    }
}
