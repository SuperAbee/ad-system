package com.abee.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author xincong yao
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrixDashboard
public class DashBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(DashBoardApplication.class, args);
    }
}
