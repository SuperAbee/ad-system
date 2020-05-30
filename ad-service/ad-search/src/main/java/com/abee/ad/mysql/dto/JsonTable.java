package com.abee.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xincong yao
 * @see TableTemplate
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonTable {

    private String tableName;
    private Integer level;

    private List<Column> insert;
    private List<Column> delete;
    private List<Column> update;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Column {
        private String column;
    }
}
