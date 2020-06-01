package com.abee.ad.index.creative;

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
public class CreativeIndex implements IndexAware<Long, CreativeObject> {
    private static Map<Long, CreativeObject> map;

    static {
        map = new ConcurrentHashMap<>();
    }

    public List<CreativeObject> fetch(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }

        List<CreativeObject> result = new ArrayList<>();
        for (Long id : ids) {
            CreativeObject o = map.get(id);
            if (o == null) {
                log.error("CreativeObject not found: {}", id);
                continue;
            }
            result.add(o);
        }

        return result;
    }

    @Override
    public CreativeObject get(Long key) {
        return map.get(key);
    }

    @Override
    public void add(Long key, CreativeObject value) {
        log.debug("CreativeIndex, before add: {}", map);
        map.put(key, value);
        log.debug("CreativeIndex, after add: {}", map);
    }

    @Override
    public void update(Long key, CreativeObject value) {
        log.debug("CreativeIndex, before update: {}", map);
        CreativeObject oldCreative = map.get(key);
        if (oldCreative == null) {
            map.put(key, value);
        } else {
            oldCreative.update(value);
        }
        log.debug("CreativeIndex, after update: {}", map);
    }

    @Override
    public void delete(Long key, CreativeObject value) {
        log.debug("CreativeIndex, before delete: {}", map);
        map.remove(key);
        log.debug("CreativeIndex, after delete: {}", map);
    }
}
