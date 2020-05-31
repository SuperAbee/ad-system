package com.abee.ad.search.vo.feature;

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
public class DistrictFeature {

    private List<Location> districts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Location {
        private String province;
        private String city;
    }
}
