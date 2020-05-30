package com.abee.ad.mysql.dto;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author xincong yao
 */
@Data
public class BinlogRowData {

    private TableTemplate tableTemplate;

    private EventType eventType;

    private List<Map<String, String>> before;

    /**
     * One event may contains several changes, So it's List
     * [{columnName, value}, ...]
     */
    private List<Map<String, String>> after;

}
