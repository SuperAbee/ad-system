package com.abee.ad.search.impl;

import com.abee.ad.index.CommonStatus;
import com.abee.ad.index.DataTable;
import com.abee.ad.index.creative.CreativeIndex;
import com.abee.ad.index.creative.CreativeObject;
import com.abee.ad.index.creativeunit.CreativeUnitIndex;
import com.abee.ad.index.district.UnitDistrictIndex;
import com.abee.ad.index.interest.UnitItIndex;
import com.abee.ad.index.keyword.UnitKeywordIndex;
import com.abee.ad.index.unit.UnitIndex;
import com.abee.ad.index.unit.UnitObject;
import com.abee.ad.search.ISearch;
import com.abee.ad.search.vo.SearchRequest;
import com.abee.ad.search.vo.SearchResponse;
import com.abee.ad.search.vo.feature.DistrictFeature;
import com.abee.ad.search.vo.feature.FeatureRelationship;
import com.abee.ad.search.vo.feature.ItFeature;
import com.abee.ad.search.vo.feature.KeywordFeature;
import com.abee.ad.search.vo.media.AdSlot;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author xincong yao
 */
@Slf4j
@Component
public class SearchImpl implements ISearch {
    @Override
    public SearchResponse fetchAds(SearchRequest request) {
        List<AdSlot> slots = request.getRequestInfo().getSlots();

        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        ItFeature itFeature = request.getFeatureInfo().getItFeature();
        DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        FeatureRelationship relationship = request.getFeatureInfo().getFeatureRelationship();

        SearchResponse response = new SearchResponse();
        Map<String, List<SearchResponse.Creative>> slot2Ads = response.getSlot2Ads();
        for (AdSlot slot : slots) {
            Set<Long> targetUnitSet;

            /**
             * pre-filter according to positionType
             */
            Set<Long> unitIdSet = DataTable.of(UnitIndex.class).match(slot.getPositionType());

            /**
             * filter according to feature
             */
            if (relationship == FeatureRelationship.AND) {
                filterDistrict(unitIdSet, districtFeature);
                filterIt(unitIdSet, itFeature);
                filterKeyword(unitIdSet, keywordFeature);

                targetUnitSet = unitIdSet;
            } else {
                targetUnitSet = getOrRelationUnits(unitIdSet,
                        keywordFeature, districtFeature, itFeature);
            }

            /**
             * sort of translation
             */
            List<UnitObject> unitIdList = DataTable.of(UnitIndex.class).fetch(targetUnitSet);

            filterUnitAndPlanStatus(unitIdList, CommonStatus.VALID);

            List<Long> creativeIds = DataTable.of(CreativeUnitIndex.class).selectAds(unitIdList);
            List<CreativeObject> cos = DataTable.of(CreativeIndex.class).fetch(creativeIds);

            filterCreativeBySlot(cos, slot.getWidth(), slot.getHeight(), slot.getType());

            List<SearchResponse.Creative> c = new ArrayList<>();
            cos.forEach(e -> c.add(SearchResponse.index2Creative(e)));

            slot2Ads.put(slot.getAdSlotCode(), c);
        }

        log.debug("fetchAds: {} - {}", JSON.toJSONString(request), JSON.toJSONString(response));
        return response;
    }

    private Set<Long> getOrRelationUnits(Set<Long> idSet, KeywordFeature keywordFeature,
                                         DistrictFeature districtFeature, ItFeature itFeature) {
        if (CollectionUtils.isEmpty(idSet)) {
            return Collections.emptySet();
        }

        Set<Long> keywordIdSet = new HashSet<>(idSet);
        Set<Long> itIdSet = new HashSet<>(idSet);
        Set<Long> districtIdSet = new HashSet<>(idSet);

        filterKeyword(keywordIdSet, keywordFeature);
        filterIt(itIdSet, itFeature);
        filterDistrict(districtIdSet, districtFeature);

        return new HashSet<>(
                CollectionUtils.union(
                        CollectionUtils.union(keywordIdSet, districtIdSet),
                        itIdSet
                )
        );
    }

    private void filterKeyword(Collection<Long> unitIds, KeywordFeature feature) {
        if (unitIds == null || unitIds.isEmpty() || feature == null || feature.getKeywords().isEmpty()) {
            return;
        }

        CollectionUtils.filter(
                unitIds,
                unitId -> DataTable.of(UnitKeywordIndex.class).match(unitId, feature.getKeywords())
        );
    }

    private void filterDistrict(Collection<Long> unitIds, DistrictFeature feature) {
        if (unitIds == null || unitIds.isEmpty() || feature == null || feature.getDistricts().isEmpty()) {
            return;
        }

        CollectionUtils.filter(
                unitIds,
                unitId -> DataTable.of(UnitDistrictIndex.class).match(unitId, feature.getDistricts())
        );
    }

    private void filterIt(Collection<Long> unitIds, ItFeature feature) {
        if (unitIds == null || unitIds.isEmpty() || feature == null || feature.getIts().isEmpty()) {
            return;
        }

        CollectionUtils.filter(
                unitIds,
                unitId -> DataTable.of(UnitItIndex.class).match(unitId, feature.getIts())
        );

    }

    private void filterUnitAndPlanStatus(List<UnitObject> units, CommonStatus status) {
        if (CollectionUtils.isEmpty(units)) {
            return;
        }

        CollectionUtils.filter(
                units,
                unit -> unit.getUnitStatus().equals(status.getStatus()) &&
                        unit.getPlanObject().getPlanStatus().equals(status.getStatus())
        );
    }

    private void filterCreativeBySlot(List<CreativeObject> cos,
                                      Integer width, Integer height,
                                      List<Integer> type) {
        if (CollectionUtils.isEmpty(cos)) {
            return;
        }

        CollectionUtils.filter(
                cos,
                co -> co.getAuditStatus().equals(CommonStatus.VALID.getStatus()) &&
                        co.getHeight().equals(height) && co.getWidth().equals(width) &&
                        type.contains(co.getType())
        );
    }
}
