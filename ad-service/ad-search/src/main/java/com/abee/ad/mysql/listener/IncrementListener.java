package com.abee.ad.mysql.listener;

import com.abee.ad.mysql.constant.Constants;
import com.abee.ad.mysql.constant.OpType;
import com.abee.ad.mysql.dto.BinlogRowData;
import com.abee.ad.mysql.dto.MySqlRowData;
import com.abee.ad.mysql.dto.TableTemplate;
import com.abee.ad.sender.ISender;
import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xincong yao
 */
@Slf4j
@Component
public class IncrementListener implements IListener {

    @Resource(name = "")
    private ISender sender;

    private AggregationListener aggregationListener;

    @Autowired
    public IncrementListener(AggregationListener aggregationListener) {
        this.aggregationListener = aggregationListener;
    }

    @Override
    @PostConstruct
    public void register() {
        log.info("increment listener register db and table info");

        Constants.table2Db.forEach((k, v) -> aggregationListener.register(k, v, this));

    }

    @Override
    public void onEvent(BinlogRowData rowData) {
        /**
         * Convert
         */
        TableTemplate tableTemplate = rowData.getTableTemplate();
        EventType eventType = rowData.getEventType();

        MySqlRowData mySqlRowData = new MySqlRowData();
        mySqlRowData.setTableName(tableTemplate.getTableName());
        mySqlRowData.setLevel(tableTemplate.getLevel());
        OpType opType = OpType.parse(eventType);
        mySqlRowData.setType(opType);

        List<String> fieldList = tableTemplate.getOpTypeFieldSetMap().get(opType);
        if (fieldList == null) {
            log.warn("{} not support for {}", opType, tableTemplate.getTableName());
            return;
        }

        for (Map<String, String> after : rowData.getAfter()) {
            Map<String, String> afterMap = new HashMap<>(8);
            for (Map.Entry<String, String> entry : after.entrySet()) {
                String colName = entry.getKey();
                String colValue = entry.getValue();
                afterMap.put(colName, colValue);
            }
            mySqlRowData.getFieldValueMap().add(afterMap);
        }


        sender.send(mySqlRowData);
    }
}
