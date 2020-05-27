package com.abee.ad.vo;

import com.abee.ad.entity.Creative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xincong yao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeResponse {

    private Long id;

    private String name;

    public CreativeResponse(Creative creative) {
        this.id = creative.getId();
        this.name = creative.getName();
    }
}
