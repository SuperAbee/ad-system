package com.abee.ad.index.interest;

import com.abee.ad.index.IndexAware;
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

/**
 * @author xincong yao
 */
@Slf4j
@Component
public class UnitItIndex implements IndexAware<String, Set<Long>> {
    private static Map<String, Set<Long>> itUnitMap;

    private static Map<Long, Set<String>> unitItMap;

    static {
        itUnitMap = new ConcurrentHashMap<>();
        unitItMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        if (StringUtils.isEmpty(key)) {
            return Collections.emptySet();
        }

        Set<Long> result = itUnitMap.get(key);
        if (result == null) {
            return Collections.emptySet();
        } else {
            return result;
        }
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.debug("UnitItIndex, before add: {}", unitItMap);

        Set<Long> unitIdSet = CommonUtils.getOrCreate(
                key, itUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.addAll(value);

        for (Long unitId : value) {
            Set<String> itSet = CommonUtils.getOrCreate(
                    unitId, unitItMap, ConcurrentSkipListSet::new);
            itSet.add(key);
        }

        log.debug("UnitItIndex, after add: {}", unitItMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("interest updating not support.");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.debug("UnitItIndex, before delete: {}", unitItMap);

        Set<Long> unitIds = CommonUtils.getOrCreate(
                key, itUnitMap, ConcurrentSkipListSet::new);
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> itSet = CommonUtils.getOrCreate(
                    unitId, unitItMap, ConcurrentSkipListSet::new);
            itSet.remove(key);
        }

        log.debug("UnitItIndex, after delete: {}", unitItMap);
    }

    public boolean match(Long unitId, List<String> its) {
        Set<String> unitIts = unitItMap.get(unitId);
        if (unitIts != null && !unitIts.isEmpty()) {
            return CollectionUtils.isSubCollection(its, unitIts);
        }

        return false;
    }
}
