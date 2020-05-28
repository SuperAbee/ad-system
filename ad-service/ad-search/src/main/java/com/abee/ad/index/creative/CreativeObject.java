package com.abee.ad.index.creative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xincong yao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeObject {

    private Long creativeId;
    private String name;
    private Integer type;
    private Integer materialType;
    private Integer height;
    private Integer width;
    private Integer auditStatus;
    private String url;

    public void update(CreativeObject o) {
        if (o.creativeId != null) {
            creativeId = o.creativeId;
        }
        if (o.name != null) {
            name = o.name;
        }
        if (o.type != null) {
            type = o.type;
        }
        if (o.materialType != null) {
            materialType = o.materialType;
        }
        if (o.height != null) {
            height = o.height;
        }
        if (o.width != null) {
            width = o.width;
        }
        if (o.auditStatus != null) {
            auditStatus = o.auditStatus;
        }
        if (o.url != null) {
            url = o.url;
        }
    }
}
