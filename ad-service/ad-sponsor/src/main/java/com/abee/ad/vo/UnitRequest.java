package com.abee.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @author xincong yao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitRequest {

    private Long planId;

    private String unitName;

    private Integer positionType;

    private Long budget;

    public boolean createValidate() {
        return planId != null && !StringUtils.isEmpty(unitName) &&
                positionType != null && budget != null;
    }
}
