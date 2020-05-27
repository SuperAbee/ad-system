package com.abee.ad.vo;

import com.abee.ad.entity.AdPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xincong yao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanResponse {

    private Long id;

    private String planName;

    public PlanResponse(AdPlan plan) {
        this.id = plan.getId();
        this.planName = plan.getPlanName();
    }

}
