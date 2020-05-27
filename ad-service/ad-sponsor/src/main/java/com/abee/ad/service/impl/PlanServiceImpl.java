package com.abee.ad.service.impl;

import com.abee.ad.constant.Constants;
import com.abee.ad.dao.AdPlanRepository;
import com.abee.ad.dao.AdUserRepository;
import com.abee.ad.entity.AdPlan;
import com.abee.ad.exception.AdException;
import com.abee.ad.service.IPlanService;
import com.abee.ad.vo.PlanGetRequest;
import com.abee.ad.vo.PlanRequest;
import com.abee.ad.vo.PlanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xincong yao
 */
@Service
public class PlanServiceImpl implements IPlanService {

    private final AdUserRepository userRepository;

    private final AdPlanRepository planRepository;

    @Autowired
    public PlanServiceImpl(AdUserRepository userRepository, AdPlanRepository planRepository) {
        this.userRepository = userRepository;
        this.planRepository = planRepository;
    }

    @Override
    @Transactional
    public PlanResponse createPlan(PlanRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        if (!userRepository.existsById(request.getUserId())) {
            throw new AdException(Constants.ErrorMsg.RECORD_NOT_FOUND);
        }

        AdPlan oldPlan = planRepository.findByUserIdAndPlanName(
                request.getUserId(), request.getPlanName());
        if (oldPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_PLAN_NAME_ERROR);
        }

        AdPlan newPlan = planRepository.save(new AdPlan(request));

        return new PlanResponse(newPlan);
    }

    @Override
    public List<AdPlan> getPlanByIds(PlanGetRequest request) throws AdException {
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        return planRepository.findAllByIdInAndUserId(
                request.getIds(), request.getUserId());
    }

    @Override
    @Transactional
    public PlanResponse updatePlan(PlanRequest request) throws AdException {
        if (!request.updateValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan oldPlan = planRepository.findByUserIdAndPlanName(
                request.getUserId(), request.getPlanName());
        if (oldPlan == null) {
            throw new AdException(Constants.ErrorMsg.RECORD_NOT_FOUND);
        }

        oldPlan.update(new AdPlan(request));

        AdPlan newPlan = planRepository.save(oldPlan);

        return new PlanResponse(newPlan);
    }

    @Override
    @Transactional
    public void deletePlan(PlanRequest request) throws AdException {
        if (!request.deleteValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan plan = planRepository.findByUserIdAndPlanName(
                request.getUserId(), request.getPlanName());
        if (plan == null) {
            throw new AdException(Constants.ErrorMsg.RECORD_NOT_FOUND);
        }

        plan.delete();

        planRepository.save(plan);
    }
}
