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
public class UnitKeywordRequest {

    private List<UnitKeyword> unitKeywords;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UnitKeyword {
        private Long unitId;
        private String keyword;
    }
}
