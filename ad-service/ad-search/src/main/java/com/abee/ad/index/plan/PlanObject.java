package com.abee.ad.index.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author xincong yao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanObject {

    private Long userId;
    private Long planId;
    private Integer planStatus;
    private Date startTime;
    private Date endTime;

    public void update(PlanObject o) {
        if (o.planId != null) {
            planId = o.planId;
        }
        if (o.userId != null) {
            userId = o.userId;
        }
        if (o.planStatus != null) {
            planStatus = o.planStatus;
        }
        if (o.startTime != null) {
            startTime = o.startTime;
        }
        if (o.endTime != null) {
            endTime = o.endTime;
        }
    }
}
