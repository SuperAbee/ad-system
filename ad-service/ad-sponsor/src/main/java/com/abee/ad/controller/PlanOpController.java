package com.abee.ad.controller;

import com.abee.ad.entity.AdPlan;
import com.abee.ad.exception.AdException;
import com.abee.ad.service.IPlanService;
import com.abee.ad.service.impl.PlanServiceImpl;
import com.abee.ad.vo.PlanGetRequest;
import com.abee.ad.vo.PlanRequest;
import com.abee.ad.vo.PlanResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xincong yao
 */
@Slf4j
@RestController
public class PlanOpController {

    private final IPlanService planService;

    @Autowired
    public PlanOpController(PlanServiceImpl planService) {
        this.planService = planService;
    }

    @PostMapping("/plan")
    public PlanResponse createPlan(
            @RequestBody PlanRequest request) throws AdException {
        log.info("ad-sponsor: createPlan -> {}", JSON.toJSONString(request));
        return planService.createPlan(request);
    }

    @GetMapping("/plan")
    public List<AdPlan> getPlanByIds(
            @RequestBody PlanGetRequest request) throws AdException {
        log.info("ad-sponsor: getPlanByIds -> {}", JSON.toJSONString(request));
        return planService.getPlanByIds(request);
    }

    @PutMapping("/plan")
    public PlanResponse updatePlan(
            @RequestBody PlanRequest request) throws AdException {
        log.info("ad-sponsor: updatePlan -> {}", JSON.toJSONString(request));
        return planService.updatePlan(request);
    }

    @DeleteMapping("/plan")
    public void deletePlan(
            @RequestBody PlanRequest request) throws AdException {
        log.info("ad-sponsor: deletePlan -> {}", JSON.toJSONString(request));
        planService.deletePlan(request);
    }
}
