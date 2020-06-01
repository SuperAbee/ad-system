package com.abee.ad.search.vo;

import com.abee.ad.index.creative.CreativeIndex;
import com.abee.ad.index.creative.CreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * @author xincong yao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    public Map<String, List<Creative>> slot2Ads = new HashMap<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Creative {
        private Long adId;
        private String url;
        private Integer width;
        private Integer height;
        private Integer type;
        private Integer materialType;

        private List<String> showMonitorUrl = Arrays.asList("www.abee.pub", "www.abee.pub");

        private List<String> clickMonitorUrl = Arrays.asList("www.abee.pub", "www.abee.pub");
    }

    public static Creative index2Creative(CreativeObject object) {
        Creative creative = new Creative();
        creative.setAdId(object.getCreativeId());
        creative.setUrl(object.getUrl());
        creative.setHeight(object.getHeight());
        creative.setWidth(object.getWidth());
        creative.setType(object.getType());
        creative.setMaterialType(object.getMaterialType());

        return creative;
    }

}
