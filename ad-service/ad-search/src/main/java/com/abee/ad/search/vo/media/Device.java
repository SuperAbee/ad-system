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
public class Device {

    private String deviceId;

    private String mac;

    private String ip;

    /**
     * Brand of device
     */
    private String model;

    /**
     * 1980 * 1080
     */
    private String displaySize;

    /**
     * 5.5 inch
     */
    private String screenSize;

    private String serialNumber;
}
