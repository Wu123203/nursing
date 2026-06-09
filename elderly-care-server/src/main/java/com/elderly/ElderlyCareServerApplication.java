package com.elderly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类
 */
@EnableTransactionManagement
@EnableScheduling
@SpringBootApplication
public class ElderlyCareServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElderlyCareServerApplication.class, args);
    }

}
