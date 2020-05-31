package com.abee.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xincong yao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Geo {

    private Float latitude;

    private Float longitude;

    private String province;

    private String city;
}
