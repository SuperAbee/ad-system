package com.abee.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author xincong yao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdPlanTable {

    private Long userId;
    private Long planId;
    private Integer planStatus;
    private Date startTime;
    private Date endTime;

}
