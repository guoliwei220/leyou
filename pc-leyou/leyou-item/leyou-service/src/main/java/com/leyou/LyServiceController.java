package com.leyou;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.leyou.mapper")
public class LyServiceController {

    public static void main(String[] args) {
        SpringApplication.run(LyServiceController.class);
        log.info("乐优商城提供服务启动成功=============================");
    }
}
