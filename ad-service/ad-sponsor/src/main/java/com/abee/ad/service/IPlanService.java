package com.abee.ad.service;

import com.abee.ad.entity.AdPlan;
import com.abee.ad.exception.AdException;
import com.abee.ad.vo.PlanGetRequest;
import com.abee.ad.vo.PlanRequest;
import com.abee.ad.vo.PlanResponse;

import java.util.List;

/**
 * @author xincong yao
 */
public interface IPlanService {

    PlanResponse createPlan(PlanRequest request) throws AdException;

    List<AdPlan> getPlanByIds(PlanGetRequest request) throws AdException;

    PlanResponse updatePlan(PlanRequest request) throws AdException;

    void deletePlan(PlanRequest request) throws AdException;
}
