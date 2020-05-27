package com.abee.ad.vo;

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
public class UnitItRequest {

    private List<UnitIt> unitIts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UnitIt {
        private Long unitId;
        private String itTag;
    }
}
