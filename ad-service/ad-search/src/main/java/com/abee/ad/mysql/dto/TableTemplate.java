package com.abee.ad.mysql.dto;

import com.abee.ad.mysql.constant.OpType;
import com.abee.ad.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xincong yao
 * @see JsonTable
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableTemplate {

    private String tableName;
    private String level;

    /**
     * Operation Type -> Sequence of columns
     */
    private Map<OpType, List<String>> opTypeFieldSetMap = new HashMap<>();

    /**
     * index of table -> column name
     * {0, 1, 2} -> {column0, column1, column2}
     */
    private Map<Integer, String> posMap = new HashMap<>();

    public static TableTemplate parse(JsonTable table) {
        TableTemplate template = new TableTemplate();

        template.setTableName(table.getTableName());
        template.setLevel(table.getLevel().toString());

        Map<OpType, List<String>> opTypeFieldSetMap = template.getOpTypeFieldSetMap();

        /**
         * Add each column to operation set according to OpType
         */
        for (JsonTable.Column column : table.getInsert()) {
            CommonUtils.getOrCreate(OpType.ADD, opTypeFieldSetMap, ArrayList::new)
                    .add(column.getColumn());
        }
        for (JsonTable.Column column : table.getUpdate()) {
            CommonUtils.getOrCreate(OpType.UPDATE, opTypeFieldSetMap, ArrayList::new)
                    .add(column.getColumn());
        }
        for (JsonTable.Column column : table.getDelete()) {
            CommonUtils.getOrCreate(OpType.DELETE, opTypeFieldSetMap, ArrayList::new)
                    .add(column.getColumn());
        }

        return template;
    }
}
