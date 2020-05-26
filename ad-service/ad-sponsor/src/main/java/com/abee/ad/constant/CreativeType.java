package com.abee.ad.constant;

import lombok.Getter;

/**
 * @author xincong yao
 */
@Getter
public enum CreativeType {

    IMAGE(0, "图片"),
    VIDEO(1, "视频"),
    TEXT(2, "文字");

    private Integer status;
    private String desc;

    CreativeType(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
