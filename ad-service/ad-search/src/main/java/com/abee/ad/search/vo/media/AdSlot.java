package com.abee.ad.search.vo.media;

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
public class AdSlot {

    private String adSlotCode;

    /**
     * From mall or bus station or somewhere else.
     */
    private Integer positionType;

    private Integer width;
    private Integer height;

    /**
     * Material type
     */
    private List<Integer> type;

    private Integer price;
}
