package com.abee.ad.vo;

import com.abee.ad.constant.CreativeMaterialType;
import com.abee.ad.constant.CreativeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @author xincong yoa
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeRequest {

    private String name;
    private CreativeType type;
    private CreativeMaterialType materialType;
    private Integer height;
    private Integer width;
    private Long size;
    private Integer duration;
    private Long userId;
    private String url;

    public boolean createValidate() {
        return !StringUtils.isEmpty(name) && type != null;
    }
}
