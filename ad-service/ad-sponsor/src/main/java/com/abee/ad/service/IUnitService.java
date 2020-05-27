package com.abee.ad.service;

import com.abee.ad.exception.AdException;
import com.abee.ad.vo.*;

/**
 * @author xincong yao
 */
public interface IUnitService {

    UnitResponse createUnit(UnitRequest request) throws AdException;

    UnitKeywordResponse createUnitKeyword(UnitKeywordRequest request) throws AdException;

    UnitItResponse createUnitIt(UnitItRequest request) throws AdException;

    UnitDistrictResponse createUnitDistrict(UnitDistrictRequest request) throws AdException;

    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException;
}
