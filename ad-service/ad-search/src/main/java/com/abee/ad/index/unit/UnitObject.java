package com.abee.ad.index.unit;

import com.abee.ad.index.plan.PlanObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xincong yao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitObject {

    private Long unitId;
    private Integer unitStatus;
    private Integer positionType;
    private Long planId;

    private PlanObject planObject;

    public void update(UnitObject o) {
        if (o.unitId != null) {
            unitId = o.unitId;
        }
        if (o.unitStatus != null) {
            unitStatus = o.unitStatus;
        }
        if (o.positionType != null) {
            positionType = o.positionType;
        }
        if (o.planId != null) {
            planId = o.planId;
        }
        if (o.planObject != null) {
            planObject = o.planObject;
        }
    }
}
