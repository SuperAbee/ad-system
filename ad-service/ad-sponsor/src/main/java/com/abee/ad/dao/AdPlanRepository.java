package com.abee.ad.dao;

import com.abee.ad.entity.AdPlan;
import com.abee.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xincong yao
 */
public interface AdPlanRepository extends JpaRepository<AdPlan, Long> {

    List<AdPlan> findAllByIdInAndUserId(List<Long> ids, Long userId);

    AdPlan findByUserIdAndPlanName(Long id, String planName);

    List<AdPlan> findAllByPlanStatus(Integer status);

}
