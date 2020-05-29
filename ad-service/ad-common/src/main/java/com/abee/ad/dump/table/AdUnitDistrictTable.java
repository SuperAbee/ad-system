package com.abee.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xincong yao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitDistrictTable {

    private Long unitId;
    private String province;
    private String city;
}
