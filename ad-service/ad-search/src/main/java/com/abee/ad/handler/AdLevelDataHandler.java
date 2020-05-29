package com.abee.ad.handler;

import com.abee.ad.dump.table.*;
import com.abee.ad.index.DataTable;
import com.abee.ad.index.IndexAware;
import com.abee.ad.index.creative.CreativeIndex;
import com.abee.ad.index.creative.CreativeObject;
import com.abee.ad.index.creativeunit.CreativeUnitIndex;
import com.abee.ad.index.creativeunit.CreativeUnitObject;
import com.abee.ad.index.district.UnitDistrictIndex;
import com.abee.ad.index.interest.UnitItObject;
import com.abee.ad.index.keyword.UnitKeywordIndex;
import com.abee.ad.index.keyword.UnitKeywordObject;
import com.abee.ad.index.plan.PlanIndex;
import com.abee.ad.index.plan.PlanObject;
import com.abee.ad.index.unit.UnitIndex;
import com.abee.ad.index.unit.UnitObject;
import com.abee.ad.mysql.constant.OpType;
import com.abee.ad.utils.CommonUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;


/**
 * @author xincong yao
 */
@Slf4j
public class AdLevelDataHandler {

    public static void handleLevel2(AdPlanTable planTable, OpType type) {
        PlanObject planObject = new PlanObject(
                planTable.getUserId(),
                planTable.getPlanId(),
                planTable.getPlanStatus(),
                planTable.getStartTime(),
                planTable.getEndTime()
        );

        handleBinlogEvent(
                DataTable.of(PlanIndex.class),
                planObject.getPlanId(),
                planObject,
                type
        );
    }

    public static void handleLevel2(CreativeTable creativeTable, OpType type) {
        CreativeObject creativeObject = new CreativeObject(
                creativeTable.getCreativeId(),
                creativeTable.getName(),
                creativeTable.getType(),
                creativeTable.getMaterialType(),
                creativeTable.getHeight(),
                creativeTable.getWidth(),
                creativeTable.getAuditStatus(),
                creativeTable.getUrl()
        );

        handleBinlogEvent(
                DataTable.of(CreativeIndex.class),
                creativeObject.getCreativeId(),
                creativeObject,
                type
        );
    }

    public static void handleLevel3(CreativeUnitTable cut, OpType type) {
        /**
         * Pre-validate
         */
        if (type == OpType.UPDATE) {
            log.error("CreativeUnitIndex not support update");
            return;
        }
        CreativeObject co = DataTable.of(CreativeIndex.class).get(cut.getCreativeId());
        UnitObject uo = DataTable.of(UnitIndex.class).get(cut.getUnitId());
        if (co == null || uo == null) {
            log.error("handleLevel3 found CreativeUnitObject error: {}", JSON.toJSONString(cut));
            return;
        }

        CreativeUnitObject cuo = new CreativeUnitObject(cut.getUnitId(), cut.getCreativeId());

        handleBinlogEvent(
                DataTable.of(CreativeUnitIndex.class),
                CommonUtils.concat(cuo.getUnitId().toString(), cuo.getCreativeId().toString()),
                cuo,
                type
        );
    }

    public static void handleLevel3(AdUnitTable ut, OpType type) {
        /**
         * To validate dependencies of plan
         */
        PlanObject po = DataTable.of(PlanIndex.class).get(ut.getPlanId());
        if (po == null) {
            log.error("handleLevel3 found PlanObject error: {}", ut.getPlanId());
            return;
        }

        UnitObject uo = new UnitObject(
                ut.getUnitId(),
                ut.getUnitStatus(),
                ut.getPositionType(),
                ut.getPlanId(),
                po
        );

        handleBinlogEvent(DataTable.of(UnitIndex.class), ut.getUnitId(), uo, type);
    }

    public static void handleLevel4(AdUnitKeywordTable kt, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("KeywordIndex not support update");
            return;
        }

        UnitObject uo = DataTable.of(UnitIndex.class).get(kt.getUnitId());
        if (uo == null) {
            log.error("handleLevel4 found UnitObject error: {}", kt.getUnitId());
            return;
        }

        UnitKeywordObject uko = new UnitKeywordObject(kt.getUnitId(), kt.getKeyword());
        Set<Long> value = new HashSet<>(1);
        value.add(uko.getUnitId());

        handleBinlogEvent(
                DataTable.of(UnitKeywordIndex.class),
                kt.getKeyword(),
                value,
                type
        );
    }

    public static void handleLevel4(AdUnitItTable it, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("InterestIndex not support update");
            return;
        }

        UnitObject io = DataTable.of(UnitIndex.class).get(it.getUnitId());
        if (io == null) {
            log.error("handleLevel4 found UnitObject error: {}", it.getUnitId());
            return;
        }

        UnitItObject uio = new UnitItObject(it.getUnitId(), it.getItTag());
        Set<Long> value = new HashSet<>(1);
        value.add(uio.getUnitId());

        handleBinlogEvent(
                DataTable.of(UnitKeywordIndex.class),
                it.getItTag(),
                value,
                type
        );
    }

    public static void handleLevel4(AdUnitDistrictTable dt, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("DistrictIndex not support update");
            return;
        }

        UnitObject uo = DataTable.of(UnitIndex.class).get(dt.getUnitId());
        if (uo == null) {
            log.error("handleLevel4 found UnitObject error: {}", dt.getUnitId());
            return;
        }

        String key = CommonUtils.concat(dt.getProvince(), dt.getCity());

        Set<Long> value = new HashSet<>(1);
        value.add(dt.getUnitId());

        handleBinlogEvent(
                DataTable.of(UnitDistrictIndex.class),
                key,
                value,
                type
        );
    }

    private static <K, V> void handleBinlogEvent(IndexAware<K, V> index, K key, V value, OpType type) {
        switch (type) {
            case ADD:
                index.add(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            default:
                break;
        }
    }
}
