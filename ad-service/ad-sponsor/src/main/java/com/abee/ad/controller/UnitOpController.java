package com.abee.ad.controller;

import com.abee.ad.exception.AdException;
import com.abee.ad.service.IUnitService;
import com.abee.ad.service.impl.UnitServiceImpl;
import com.abee.ad.vo.*;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xincong yao
 */
@Slf4j
@RestController
public class UnitOpController {

    private final IUnitService unitService;

    @Autowired
    public UnitOpController(UnitServiceImpl unitService) {
        this.unitService = unitService;
    }

    @PostMapping("/unit")
    public UnitResponse createUnit(@RequestBody UnitRequest request) throws AdException {
        log.info("ad-sponsor: createUnit -> {}", JSON.toJSONString(request));
        return unitService.createUnit(request);
    }

    @PostMapping("/unitKeyword")
    public UnitKeywordResponse createUnitKeyword(
            @RequestBody UnitKeywordRequest request) throws AdException {
        log.info("ad-sponsor: createUnitKeyword -> {}", JSON.toJSONString(request));
        return unitService.createUnitKeyword(request);
    }

    @PostMapping("/unitIt")
    public UnitItResponse createUnitIt(
            @RequestBody UnitItRequest request) throws AdException {
        log.info("ad-sponsor: createUnitIt -> {}", JSON.toJSONString(request));
        return unitService.createUnitIt(request);
    }

    @PostMapping("/unitDistrict")
    public UnitDistrictResponse createUnitDistrict(
            @RequestBody UnitDistrictRequest request) throws AdException {
        log.info("ad-sponsor: createUnitDistrict -> {}", JSON.toJSONString(request));
        return unitService.createUnitDistrict(request);
    }

    @PostMapping("/creativeUnit")
    public CreativeUnitResponse createCreativeUnit(
            @RequestBody CreativeUnitRequest request) throws AdException {
        log.info("ad-sponsor: createCreativeUnit -> {}", JSON.toJSONString(request));
        return unitService.createCreativeUnit(request);
    }
}
