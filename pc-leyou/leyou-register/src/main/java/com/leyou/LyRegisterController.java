package com.leyou;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
@EnableEurekaServer
@SpringBootApplication
@Slf4j
public class LyRegisterController {
    public static void main(String[] args){
        SpringApplication.run(LyRegisterController.class,args);
        log.info("乐优注册中心启动完成===================================");
    }
}
