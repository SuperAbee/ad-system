package com.abee.ad.constant;

import lombok.Getter;

/**
 * @author xincong yao
 */
@Getter
public enum CreativeMaterialType {

    JPG(0, "jpg"),
    BMP(1, "bmp"),

    MP4(10, "mp4"),
    AVI(11, "avi"),

    TXT(20, "txt");

    private Integer type;
    private String desc;

    CreativeMaterialType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
