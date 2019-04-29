package com.leyou;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class LyUploadController {
    public static void main(String[] args) {
        SpringApplication.run(LyUploadController.class);
        log.info("上传文件服务启动完成");
    }
}
