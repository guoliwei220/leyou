package com.leyou;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
@Slf4j
@EnableZuulProxy
@SpringCloudApplication
public class LyGatewayController {
    public static void main(String[] args) {
        SpringApplication.run(LyGatewayController.class);
        log.info("网关启动完成======================================");
    }
}
