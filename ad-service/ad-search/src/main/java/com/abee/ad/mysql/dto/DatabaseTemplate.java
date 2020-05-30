package com.abee.ad.mysql.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xincong yao
 * @see JsonDatabase
 */
@Data
public class DatabaseTemplate {

    private String database;

    private Map<String, TableTemplate> tableTemplateMap = new HashMap<>();

    public static DatabaseTemplate parse(JsonDatabase database) {
        DatabaseTemplate template = new DatabaseTemplate();
        template.setDatabase(database.getDatabase());

        /**
         * Translate each jsonTable to tableTemplate
         */
        Map<String, TableTemplate> tableTemplateMap = template.getTableTemplateMap();
        for (JsonTable jsonTable : database.getTableList()) {
            tableTemplateMap.put(jsonTable.getTableName(), TableTemplate.parse(jsonTable));
        }

        return template;
    }

}
