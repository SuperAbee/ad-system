package com.abee.ad.dao;

import com.abee.ad.entity.AdUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xincong yao
 */
public interface AdUnitRepository extends JpaRepository<AdUnit, Long> {

    AdUnit findByPlanIdAndUnitName(Long unitId, String unitName);

    List<AdUnit> findAllByUnitStatus(Integer unitStatus);
}
