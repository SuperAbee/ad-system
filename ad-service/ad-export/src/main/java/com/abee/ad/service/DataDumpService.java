package com.abee.ad.service;

import com.abee.ad.constant.CommonStatus;
import com.abee.ad.dao.AdPlanRepository;
import com.abee.ad.dao.AdUnitRepository;
import com.abee.ad.dao.CreativeRepository;
import com.abee.ad.dao.CreativeUnitRepository;
import com.abee.ad.dao.condition.AdUnitDistrictRepository;
import com.abee.ad.dao.condition.AdUnitItRepository;
import com.abee.ad.dao.condition.AdUnitKeywordRepository;
import com.abee.ad.dump.table.*;
import com.abee.ad.entity.AdPlan;
import com.abee.ad.entity.AdUnit;
import com.abee.ad.entity.Creative;
import com.abee.ad.entity.condition.AdUnitDistrict;
import com.abee.ad.entity.condition.AdUnitIt;
import com.abee.ad.entity.condition.AdUnitKeyword;
import com.abee.ad.entity.condition.CreativeUnit;
import com.abee.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xincong yao
 */
@Slf4j
@Service
public class DataDumpService {

    private final AdPlanRepository planRepository;
    private final AdUnitRepository unitRepository;
    private final CreativeRepository creativeRepository;
    private final CreativeUnitRepository creativeUnitRepository;
    private final AdUnitItRepository itRepository;
    private final AdUnitDistrictRepository districtRepository;
    private final AdUnitKeywordRepository keywordRepository;

    @Autowired
    public DataDumpService(AdPlanRepository planRepository,
                           AdUnitRepository unitRepository,
                           CreativeRepository creativeRepository,
                           CreativeUnitRepository creativeUnitRepository,
                           AdUnitItRepository itRepository,
                           AdUnitDistrictRepository districtRepository,
                           AdUnitKeywordRepository keywordRepository) {
        this.planRepository = planRepository;
        this.unitRepository = unitRepository;
        this.creativeRepository = creativeRepository;
        this.creativeUnitRepository = creativeUnitRepository;
        this.itRepository = itRepository;
        this.districtRepository = districtRepository;
        this.keywordRepository = keywordRepository;
    }

    public void dumpAdPlanTable(String filename) {
        List<AdPlan> plans = planRepository.findAllByPlanStatus(
                CommonStatus.VALID.getStatus());
        if (plans.isEmpty()) {
            return;
        }

        List<AdPlanTable> planTables = new ArrayList<>();
        plans.forEach(p -> planTables.add(
                new AdPlanTable(
                        p.getUserId(),
                        p.getId(),
                        p.getPlanStatus(),
                        p.getStartDate(),
                        p.getEndDate()
                )
        ));

        if (!CommonUtils.toFile(filename, planTables)) {
            log.error("dumpAdPlanTable error");
        }
    }

    public void dumpAdUnitTable(String filename) {
        List<AdUnit> units = unitRepository.findAllByUnitStatus(
                CommonStatus.VALID.getStatus());
        if (units.isEmpty()) {
            return;
        }

        List<AdUnitTable> unitTables = new ArrayList<>();
        units.forEach(u -> unitTables.add(
                new AdUnitTable(
                        u.getId(),
                        u.getUnitStatus(),
                        u.getPositionType(),
                        u.getPlanId()
                )
        ));

        if (!CommonUtils.toFile(filename, unitTables)) {
            log.error("dumpAdUnitTable error");
        }
    }

    public void dumpCreativeTable(String filename) {
        List<Creative> cs = creativeRepository.findAll();
        if (cs.isEmpty()) {
            return;
        }

        List<CreativeTable> creativeTables = new ArrayList<>();
        cs.forEach(c -> creativeTables.add(
                new CreativeTable(
                        c.getId(),
                        c.getName(),
                        c.getType(),
                        c.getMaterialType(),
                        c.getHeight(),
                        c.getWidth(),
                        c.getAuditStatus(),
                        c.getUrl()
                )
        ));

        if (!CommonUtils.toFile(filename, creativeTables)) {
            log.error("dumpCreativeTable error");
        }
    }

    public void dumpCreativeUnitTable(String filename) {
        List<CreativeUnit> cus = creativeUnitRepository.findAll();
        if (cus.isEmpty()) {
            return;
        }

        List<CreativeUnitTable> creativeUnitTables = new ArrayList<>();
        cus.forEach(cu -> creativeUnitTables.add(
                new CreativeUnitTable(
                        cu.getCreativeId(),
                        cu.getUnitId()
                )
        ));

        if (!CommonUtils.toFile(filename, creativeUnitTables)) {
            log.error("dumpCreativeUnitTable error");
        }
    }

    public void dumpUnitDistrictTable(String filename) {
        List<AdUnitDistrict> ds = districtRepository.findAll();
        if (ds.isEmpty()) {
            return;
        }

        List<AdUnitDistrictTable> unitDistrictTables = new ArrayList<>();
        ds.forEach(d -> unitDistrictTables.add(
                new AdUnitDistrictTable(
                        d.getUnitId(),
                        d.getProvince(),
                        d.getCity()
                )
        ));

        if (!CommonUtils.toFile(filename, unitDistrictTables)) {
            log.error("dumpUnitDistrictTable error");
        }
    }

    public void dumpUnitItTable(String filename) {
        List<AdUnitIt> its = itRepository.findAll();
        if (its.isEmpty()) {
            return;
        }

        List<AdUnitItTable> unitItTables = new ArrayList<>();
        its.forEach(it -> unitItTables.add(
                new AdUnitItTable(
                        it.getUnitId(),
                        it.getItTag()
                )
        ));

        if (!CommonUtils.toFile(filename, unitItTables)) {
            log.error("dumpUnitItTable error");
        }
    }

    public void dumpUnitKeywordTable(String filename) {
        List<AdUnitKeyword> keys = keywordRepository.findAll();
        if (keys.isEmpty()) {
            return;
        }

        List<AdUnitKeywordTable> unitKeywordTables = new ArrayList<>();
        keys.forEach(k -> unitKeywordTables.add(
                new AdUnitKeywordTable(
                        k.getUnitId(),
                        k.getKeyword()
                )
        ));

        if (!CommonUtils.toFile(filename, unitKeywordTables)) {
            log.error("dumpUnitKeywordTable error");
        }
    }

}
