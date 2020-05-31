package com.abee.ad.index.plan;

import com.abee.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xincong yao
 */
@Slf4j
@Component
public class PlanIndex implements IndexAware<Long, PlanObject> {

    private static Map<Long, PlanObject> map;

    static {
        map = new ConcurrentHashMap<>();
    }

    @Override
    public PlanObject get(Long key) {
        return map.get(key);
    }

    @Override
    public void add(Long key, PlanObject value) {
        log.debug("PlanIndex, before add: {}", map);
        map.put(key, value);
        log.debug("PlanIndex, after add: {}", map);
    }

    @Override
    public void update(Long key, PlanObject value) {
        log.debug("PlanIndex, before update: {}", map);
        PlanObject oldPlan = map.get(key);
        if (oldPlan == null) {
            map.put(key, value);
        } else {
            oldPlan.update(value);
        }
        log.debug("PlanIndex, after update: {}", map);
    }

    @Override
    public void delete(Long key, PlanObject value) {
        log.debug("PlanIndex, before delete: {}", map);
        map.remove(key);
        log.debug("PlanIndex, after delete: {}", map);
    }
}
