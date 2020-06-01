package com.abee.ad.index.unit;

import com.abee.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xincong yao
 */
@Slf4j
@Component
public class UnitIndex implements IndexAware<Long, UnitObject> {

    private static Map<Long, UnitObject> map;

    static {
        map = new ConcurrentHashMap<>();
    }

    public Set<Long> match(Integer positionType) {
        Set<Long> result = new HashSet<>();

        map.forEach((k, v) -> {
            if (UnitObject.isSlotTypeValid(positionType, v.getPositionType())) {
                result.add(k);
            }
        });

        return result;
    }

    public List<UnitObject> fetch(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }

        List<UnitObject> result = new ArrayList<>();
        ids.forEach(e -> {
            UnitObject o = map.get(e);
            if (o == null) {
                log.error("UnitObject not found: {}", e);
                return;
            }
            result.add(o);
        });

        return result;
    }

    @Override
    public UnitObject get(Long key) {
        return map.get(key);
    }

    @Override
    public void add(Long key, UnitObject value) {
        log.debug("UnitIndex, before add: {}", map);
        map.put(key, value);
        log.debug("UnitIndex, after add: {}", map);
    }

    @Override
    public void update(Long key, UnitObject value) {
        log.debug("UnitIndex, before update: {}", map);
        UnitObject oldPlan = map.get(key);
        if (oldPlan == null) {
            map.put(key, value);
        } else {
            oldPlan.update(value);
        }
        log.debug("UnitIndex, after update: {}", map);
    }

    @Override
    public void delete(Long key, UnitObject value) {
        log.debug("UnitIndex, before delete: {}", map);
        map.remove(key);
        log.debug("UnitIndex, after delete: {}", map);
    }
}
