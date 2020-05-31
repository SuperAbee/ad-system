package com.abee.ad.index.creativeunit;

import com.abee.ad.index.IndexAware;
import com.abee.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author xincong yao
 */
@Slf4j
@Component
public class CreativeUnitIndex implements IndexAware<String, CreativeUnitObject> {

    /**
     * Key of objectMap: object.getCreativeId() + ":" + object.getUnitId()
     */
    private static Map<String, CreativeUnitObject> objectMap;
    private static Map<Long, Set<Long>> creativeUnitMap;
    private static Map<Long, Set<Long>> unitCreativeMap;

    static {
        objectMap = new ConcurrentHashMap<>();
        creativeUnitMap = new ConcurrentHashMap<>();
        unitCreativeMap = new ConcurrentHashMap<>();
    }

    @Override
    public CreativeUnitObject get(String key) {
        return objectMap.get(key);
    }

    @Override
    public void add(String key, CreativeUnitObject value) {
        log.debug("CreativeUnitIndex before add: {}", objectMap);

        objectMap.put(key, value);

        Set<Long> unitSet = CommonUtils.getOrCreate(
                value.getCreativeId(), creativeUnitMap, ConcurrentSkipListSet::new);
        unitSet.add(value.getUnitId());

        Set<Long> creativeSet = CommonUtils.getOrCreate(
                value.getUnitId(), unitCreativeMap, ConcurrentSkipListSet::new);
        creativeSet.add(value.getCreativeId());

        log.debug("CreativeUnitIndex, after add: {}", objectMap);
    }

    @Override
    public void update(String key, CreativeUnitObject value) {
        log.error("CreativeUnitIndex updating not support");
    }

    @Override
    public void delete(String key, CreativeUnitObject value) {
        log.debug("CreativeUnitIndex, before remove: {}", objectMap);

        objectMap.remove(key);

        Set<Long> unitSet = CommonUtils.getOrCreate(
                value.getCreativeId(), creativeUnitMap, ConcurrentSkipListSet::new);
        unitSet.remove(value.getUnitId());

        Set<Long> creativeSet = CommonUtils.getOrCreate(
                value.getUnitId(), unitCreativeMap, ConcurrentSkipListSet::new);
        creativeSet.remove(value.getCreativeId());

        log.debug("CreativeUnitIndex, after remove: {}", objectMap);
    }
}
