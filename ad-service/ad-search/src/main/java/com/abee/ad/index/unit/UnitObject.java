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

    private  static boolean isOpen(int positionType) {
        return (UnitConstants.POSITION_TYPE.OPEN & positionType) > 0;
    }

    private  static boolean isPreMovie(int positionType) {
        return (UnitConstants.POSITION_TYPE.PRE_MOVIE & positionType) > 0;
    }

    private  static boolean isMidMovie(int positionType) {
        return (UnitConstants.POSITION_TYPE.MID_MOVIE & positionType) > 0;
    }

    private  static boolean isPauseMovie(int positionType) {
        return (UnitConstants.POSITION_TYPE.PAUSE_MOVIE & positionType) > 0;
    }

    private  static boolean isPostMovie(int positionType) {
        return (UnitConstants.POSITION_TYPE.POST_MOVIE & positionType) > 0;
    }

    public static boolean isSlotTypeValid(int slotType, int positionType) {
        switch (slotType) {
            case UnitConstants.POSITION_TYPE.OPEN:
                return isOpen(positionType);
            case UnitConstants.POSITION_TYPE.PRE_MOVIE:
                return isPreMovie(positionType);
            case UnitConstants.POSITION_TYPE.POST_MOVIE:
                return isPostMovie(positionType);
            case UnitConstants.POSITION_TYPE.MID_MOVIE:
                return isMidMovie(positionType);
            case UnitConstants.POSITION_TYPE.PAUSE_MOVIE:
                return isPauseMovie(positionType);
            default:
                return false;
        }
    }
}
