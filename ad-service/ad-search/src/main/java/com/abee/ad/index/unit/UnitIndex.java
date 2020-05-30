package com.abee.ad.index.unit;

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
public class UnitIndex implements IndexAware<Long, UnitObject> {

    private static Map<Long, UnitObject> map;

    static {
        map = new ConcurrentHashMap<>();
    }

    @Override
    public UnitObject get(Long key) {
        return map.get(key);
    }

    @Override
    public void add(Long key, UnitObject value) {
        log.info("UnitIndex, before add: {}", map);
        map.put(key, value);
        log.info("UnitIndex, after add: {}", map);
    }

    @Override
    public void update(Long key, UnitObject value) {
        log.info("UnitIndex, before update: {}", map);
        UnitObject oldPlan = map.get(key);
        if (oldPlan == null) {
            map.put(key, value);
        } else {
            oldPlan.update(value);
        }
        log.info("UnitIndex, after update: {}", map);
    }

    @Override
    public void delete(Long key, UnitObject value) {
        log.info("UnitIndex, before delete: {}", map);
        map.remove(key);
        log.info("UnitIndex, after delete: {}", map);
    }
}