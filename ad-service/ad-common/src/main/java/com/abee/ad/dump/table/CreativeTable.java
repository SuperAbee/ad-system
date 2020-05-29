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
public class CreativeTable {

    private Long creativeId;
    private String name;
    private Integer type;
    private Integer materialType;
    private Integer height;
    private Integer width;
    private Integer auditStatus;
    private String url;
}
