package com.abee.ad.controller;

import com.abee.ad.annotation.IgnoreResponseAdvice;
import com.abee.ad.client.SponsorClient;
import com.abee.ad.client.vo.Plan;
import com.abee.ad.client.vo.PlanGetRequest;
import com.abee.ad.vo.CommonResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author xincong yao
 */
@Slf4j
@RestController
public class SearchController {

    private final RestTemplate restTemplate;

    private final SponsorClient sponsorClient;

    @Autowired
    public SearchController(RestTemplate restTemplate, SponsorClient sponsorClient) {
        this.restTemplate = restTemplate;
        this.sponsorClient = sponsorClient;
    }

    @IgnoreResponseAdvice
    @PostMapping("/plans")
    public CommonResponse<List<Plan>> getPlans(@RequestBody  PlanGetRequest request) {
        log.info("ad-search: getPlans -> {}", JSON.toJSONString(request));
        return sponsorClient.getPlans(request);
    }

//    @IgnoreResponseAdvice
//    @PostMapping("/plans")
//    public CommonResponse<List<Plan>> getPlans(@RequestBody  PlanGetRequest request) {
//        log.info("ad-search: getPlans -> {}", JSON.toJSONString(request));
//        return restTemplate.postForEntity(
//                "http://ad-sponsor-service/ad-sponsor/plans",
//                request,
//                CommonResponse.class
//        ).getBody();
//    }
}
