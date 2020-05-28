package com.abee.ad.index.creative;

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
public class CreativeIndex implements IndexAware<Long, CreativeObject> {
    private static Map<Long, CreativeObject> map;

    static {
        map = new ConcurrentHashMap<>();
    }

    @Override
    public CreativeObject get(Long key) {
        return map.get(key);
    }

    @Override
    public void add(Long key, CreativeObject value) {
        log.info("CreativeIndex, before add: {}", map);
        map.put(key, value);
        log.info("CreativeIndex, after add: {}", map);
    }

    @Override
    public void update(Long key, CreativeObject value) {
        log.info("CreativeIndex, before update: {}", map);
        CreativeObject oldCreative = map.get(key);
        if (oldCreative == null) {
            map.put(key, value);
        } else {
            oldCreative.update(value);
        }
        log.info("CreativeIndex, after update: {}", map);
    }

    @Override
    public void delete(Long key, CreativeObject value) {
        log.info("CreativeIndex, before delete: {}", map);
        map.remove(key);
        log.info("CreativeIndex, after delete: {}", map);
    }
}
