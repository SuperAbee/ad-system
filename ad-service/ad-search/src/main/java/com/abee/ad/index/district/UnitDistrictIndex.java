package com.abee.ad.index.district;

import com.abee.ad.index.IndexAware;
import com.abee.ad.search.vo.feature.DistrictFeature;
import com.abee.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * @author xincong yao
 */
@Slf4j
@Component
public class UnitDistrictIndex implements IndexAware<String, Set<Long>> {
    private static Map<String, Set<Long>> districtUnitMap;

    private static Map<Long, Set<String>> unitDistrictMap;

    static {
        districtUnitMap = new ConcurrentHashMap<>();
        unitDistrictMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        if (StringUtils.isEmpty(key)) {
            return Collections.emptySet();
        }

        Set<Long> result = districtUnitMap.get(key);
        if (result == null) {
            return Collections.emptySet();
        } else {
            return result;
        }
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.debug("UnitDistrictIndex, before add: {}", unitDistrictMap);

        Set<Long> unitIdSet = CommonUtils.getOrCreate(
                key, districtUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.addAll(value);

        for (Long unitId : value) {
            Set<String> districtSet = CommonUtils.getOrCreate(
                    unitId, unitDistrictMap, ConcurrentSkipListSet::new);
            districtSet.add(key);
        }

        log.debug("UnitDistrictIndex, after add: {}", unitDistrictMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("district updating not support.");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.debug("UnitDistrictIndex, before delete: {}", unitDistrictMap);

        Set<Long> unitIds = CommonUtils.getOrCreate(
                key, districtUnitMap, ConcurrentSkipListSet::new);
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> districtSet = CommonUtils.getOrCreate(
                    unitId, unitDistrictMap, ConcurrentSkipListSet::new);
            districtSet.remove(key);
        }

        log.debug("UnitDistrictIndex, after delete: {}", unitDistrictMap);
    }

    public boolean match(Long unitId, List<DistrictFeature.Location> districts) {
        Set<String> unitDistricts = unitDistrictMap.get(unitId);
        if (unitDistricts != null && !unitDistricts.isEmpty()) {
            List<String> targetDistricts = districts.stream()
                    .map(d -> CommonUtils.concat(d.getProvince(), d.getCity()))
                    .collect(Collectors.toList());
            return CollectionUtils.isSubCollection(targetDistricts, unitDistricts);
        }

        return false;
    }
}
