package com.abee.ad.mysql.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xincong yao
 * @see Template
 */
@Data
public class ParseTemplate {

    private String database;

    private Map<String, TableTemplate> tableTemplateMap = new HashMap<>();

    public static ParseTemplate parse(Template _template) {
        ParseTemplate template = new ParseTemplate();
        template.setDatabase(_template.getDatabase());

    }

}
