package com.abee.ad.search.vo;

import com.abee.ad.search.vo.feature.DistrictFeature;
import com.abee.ad.search.vo.feature.FeatureRelationship;
import com.abee.ad.search.vo.feature.ItFeature;
import com.abee.ad.search.vo.feature.KeywordFeature;
import com.abee.ad.search.vo.media.AdSlot;
import com.abee.ad.search.vo.media.App;
import com.abee.ad.search.vo.media.Device;
import com.abee.ad.search.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xincong yao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {

    private String mediaId;

    private RequestInfo requestInfo;

    private FeatureInfo featureInfo;

    /**
     * Basic information
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestInfo {
        private String requestId;

        private List<AdSlot> slots;

        private App app;

        private Geo geo;

        private Device device;
    }

    /**
     * Match information.
     * Keyword as a example.
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FeatureInfo {

        private KeywordFeature keywordFeature;
        private DistrictFeature districtFeature;
        private ItFeature itFeature;

        private FeatureRelationship featureRelationship = FeatureRelationship.AND;
    }

}
