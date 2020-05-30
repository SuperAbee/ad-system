package com.abee.ad.mysql.listener;

import com.abee.ad.mysql.TemplateHandler;
import com.abee.ad.mysql.dto.BinlogRowData;
import com.abee.ad.mysql.dto.TableTemplate;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Listen and handle data from binlog file
 * and parse {@code event.getData()} to {@link BinlogRowData}
 * @author xincong yao
 */
@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private String databaseName;
    private String tableName;

    private Map<String, IListener> listenerMap = new HashMap<>();

    private final TemplateHandler templateHandler;

    @Autowired
    public AggregationListener(TemplateHandler templateHandler) {
        this.templateHandler = templateHandler;
    }

    public void register(String dbName, String tName, IListener listener) {
        log.info("register binlog listener: {}-{}", dbName, tName);
        listenerMap.put(genKey(dbName, tName), listener);
    }

    @Override
    public void onEvent(Event event) {
        EventType eventType = event.getHeader().getEventType();

        log.debug("Event Type: {}", eventType.toString());

        /**
         * Log meta data.
         */
        if (eventType == EventType.TABLE_MAP) {
            TableMapEventData data = event.getData();
            this.databaseName = data.getDatabase();
            this.tableName = data.getTable();
            return;
        }

        /**
         * validate
         */
        if (eventType != EventType.EXT_WRITE_ROWS &&
                eventType != EventType.EXT_DELETE_ROWS &&
                eventType != EventType.EXT_UPDATE_ROWS) {
            return;
        }
        if (StringUtils.isEmpty(databaseName) || StringUtils.isEmpty(tableName)) {
            log.error("no meta data event");
            return;
        }

        /**
         * Find the event need to be handled.
         */
        IListener listener = listenerMap.get(genKey(databaseName, tableName));
        if (listener == null) {
            log.debug("skip: {}", genKey(databaseName, tableName));
            return;
        }

        /**
         * Event.data -> BinlogRowData
         */
        try {
            BinlogRowData rowData = parse(event.getData());
            if (rowData == null) {
                return;
            }
            rowData.setEventType(eventType);
            listener.onEvent(rowData);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            databaseName = "";
            tableName = "";
        }
    }

    public BinlogRowData parse(EventData data) {
        TableTemplate template = templateHandler.getTable(tableName);
        if (template == null) {
            log.warn("table {} not found", tableName);
            return null;
        }

        List<Map<String, String>> afterMapList = new ArrayList<>();
        for (Serializable[] after : getAfterValues(data)) {
            Map<String, String> afterMap = new HashMap<>();

            for (int i = 0; i < after.length; i++) {
                String colName = template.getPosMap().get(i);
                if (colName == null) {
                    log.debug("ignore position {} in {}", i, tableName);
                    continue;
                }
                String colValue = after[i].toString();

                afterMap.put(colName, colValue);
            }

            afterMapList.add(afterMap);
        }

        BinlogRowData binlogRowData = new BinlogRowData();
        binlogRowData.setAfter(afterMapList);
        binlogRowData.setTableTemplate(template);

        return binlogRowData;
    }

    private List<Serializable[]> getAfterValues(EventData eventData) {
        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }
        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows()
                    .stream().map(Map.Entry::getValue).collect(Collectors.toList());
        }
        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }

        return Collections.emptyList();
    }

    private String genKey(String dbName, String tName) {
        return dbName + ":" + tName;
    }
}
