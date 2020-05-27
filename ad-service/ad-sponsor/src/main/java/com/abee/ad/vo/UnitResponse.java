package com.abee.ad.vo;

import com.abee.ad.entity.AdUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xincong yao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitResponse {

    private Long id;

    private String unitName;

    public UnitResponse(AdUnit unit) {
        setId(unit.getId());
        setUnitName(unit.getUnitName());
    }
}
