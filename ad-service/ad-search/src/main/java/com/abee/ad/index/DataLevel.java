package com.abee.ad.index;

import lombok.Getter;

/**
 * @author xincong yao
 */
@Getter
public enum DataLevel {

    LEVEL2("2", "level 2"),
    LEVEL3("3", "level 3"),
    LEVEL4("4", "level 4");

    private String level;
    private String desc;

    DataLevel(String level, String desc) {
        this.desc = desc;
        this.level = level;
    }
}
