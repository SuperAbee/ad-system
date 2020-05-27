package com.abee.ad.service.impl;

import com.abee.ad.constant.Constants;
import com.abee.ad.dao.AdPlanRepository;
import com.abee.ad.dao.AdUnitRepository;
import com.abee.ad.dao.CreativeRepository;
import com.abee.ad.dao.CreativeUnitRepository;
import com.abee.ad.dao.condition.AdUnitDistrictRepository;
import com.abee.ad.dao.condition.AdUnitItRepository;
import com.abee.ad.dao.condition.AdUnitKeywordRepository;
import com.abee.ad.entity.AdUnit;
import com.abee.ad.entity.condition.AdUnitDistrict;
import com.abee.ad.entity.condition.AdUnitIt;
import com.abee.ad.entity.condition.AdUnitKeyword;
import com.abee.ad.entity.condition.CreativeUnit;
import com.abee.ad.exception.AdException;
import com.abee.ad.service.IUnitService;
import com.abee.ad.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xincong yao
 */
@Service
public class UnitServiceImpl implements IUnitService {

    private final AdPlanRepository planRepository;
    private final AdUnitRepository unitRepository;

    private final AdUnitKeywordRepository keywordRepository;
    private final AdUnitItRepository itRepository;
    private final AdUnitDistrictRepository districtRepository;

    private final CreativeRepository creativeRepository;
    private final CreativeUnitRepository creativeUnitRepository;

    @Autowired
    public UnitServiceImpl(AdPlanRepository planRepository, AdUnitRepository unitRepository,
                           AdUnitKeywordRepository keywordRepository,
                           AdUnitItRepository itRepository,
                           AdUnitDistrictRepository districtRepository,
                           CreativeRepository creativeRepository,
                           CreativeUnitRepository creativeUnitRepository) {
        this.planRepository = planRepository;
        this.unitRepository = unitRepository;
        this.keywordRepository = keywordRepository;
        this.itRepository = itRepository;
        this.districtRepository = districtRepository;
        this.creativeRepository = creativeRepository;
        this.creativeUnitRepository = creativeUnitRepository;
    }

    @Override
    public UnitResponse createUnit(UnitRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        if (!planRepository.existsById(request.getPlanId())) {
            throw new AdException(Constants.ErrorMsg.RECORD_NOT_FOUND);
        }

        AdUnit oldUnit = unitRepository.findByPlanIdAndUnitName(
                request.getPlanId(), request.getUnitName());
        if (oldUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_UNIT_NAME_ERROR);
        }

        AdUnit unit = unitRepository.save(new AdUnit(request));

        return new UnitResponse(unit);
    }

    @Override
    public UnitKeywordResponse createUnitKeyword(UnitKeywordRequest request) throws AdException {
        List<Long> unitIds = request.getUnitKeywords().stream()
                .map(UnitKeywordRequest.UnitKeyword::getUnitId)
                .collect(Collectors.toList());
        if (!isUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdUnitKeyword> unitKeywords = new ArrayList<>();
        request.getUnitKeywords().forEach(i -> unitKeywords.add(new AdUnitKeyword(i)));

        List<Long> result = keywordRepository.saveAll(unitKeywords).stream()
                .map(AdUnitKeyword::getId)
                .collect(Collectors.toList());

        return new UnitKeywordResponse(result);
    }

    @Override
    public UnitItResponse createUnitIt(UnitItRequest request) throws AdException {
        List<Long> unitIds = request.getUnitIts().stream()
                .map(UnitItRequest.UnitIt::getUnitId)
                .collect(Collectors.toList());
        if (!isUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdUnitIt> unitIts = new ArrayList<>();
        request.getUnitIts().forEach(i -> unitIts.add(new AdUnitIt(i)));

        List<Long> result = itRepository.saveAll(unitIts).stream()
                .map(AdUnitIt::getId)
                .collect(Collectors.toList());

        return new UnitItResponse(result);
    }

    @Override
    public UnitDistrictResponse createDistrict(UnitDistrictRequest request) throws AdException {
        List<Long> unitIds = request.getUnitDistricts().stream()
                .map(UnitDistrictRequest.UnitDistrict::getUnitId)
                .collect(Collectors.toList());
        if (!isUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdUnitDistrict> unitDistricts = new ArrayList<>();
        request.getUnitDistricts().forEach(i -> unitDistricts.add(new AdUnitDistrict(i)));

        List<Long> result = districtRepository.saveAll(unitDistricts).stream()
                .map(AdUnitDistrict::getId)
                .collect(Collectors.toList());

        return new UnitDistrictResponse(result);
    }

    @Override
    public CreativeUnitResponse createCreativeUnit(
            CreativeUnitRequest request) throws AdException {
        List<Long> creativeIds = request.getItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getCreativeId)
                .collect(Collectors.toList());
        List<Long> unitIds = request.getItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getUnitId)
                .collect(Collectors.toList());
        if (!isCreativeExist(creativeIds) && !isUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<CreativeUnit> cu = new ArrayList<>();
        request.getItems().forEach(i -> cu.add(new CreativeUnit(i)));
        List<Long> result = creativeUnitRepository.saveAll(cu).stream()
                .map(CreativeUnit::getId)
                .collect(Collectors.toList());

        return new CreativeUnitResponse(result);
    }

    private boolean isUnitExist(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        return unitRepository.findAllById(ids).size() ==
                new HashSet<>(ids).size();
    }

    private boolean isCreativeExist(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        return creativeRepository.findAllById(ids).size() ==
                new HashSet<>(ids).size();
    }
}
