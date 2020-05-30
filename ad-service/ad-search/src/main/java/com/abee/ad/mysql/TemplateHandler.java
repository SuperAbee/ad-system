package com.abee.ad.mysql;

import com.abee.ad.mysql.constant.OpType;
import com.abee.ad.mysql.dto.DatabaseTemplate;
import com.abee.ad.mysql.dto.JsonDatabase;
import com.abee.ad.mysql.dto.TableTemplate;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @function Load template.json file, then invoke {@code DatabaseTemplate.parse()}
 * @function Initialize posMap
 * @author xincong yao
 */
@Slf4j
@Component
public class TemplateHandler {

    public DatabaseTemplate template;
    public JdbcTemplate jdbcTemplate;

    private String SQL_SCHEMA = "select table_schema, table_name, column_name, ordinal_position " +
            "from information_schema.columns " +
            "where table_schema = ? and table_name = ?";

    @Autowired
    public TemplateHandler(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void init() {
        loadJson("template.json");
    }

    /**
     * Get table template from database template by table name.
     * @param tableName
     * @return table template
     */
    public TableTemplate getTable(String tableName) {
        return template.getTableTemplateMap().get(tableName);
    }

    private void loadJson(String path) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);

        try {
            JsonDatabase jsonDatabase = JSON.parseObject(
                    inputStream, Charset.defaultCharset(), DatabaseTemplate.class);
            template = DatabaseTemplate.parse(jsonDatabase);
            loadMeta();
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException("fail to load template.json");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch(IOException e) {
                    log.error(e.getLocalizedMessage());
                }
            }
        }
    }

    /**
     * @purpose tableTemplate.getPosMap().put(pos - 1, colName);
     * To initialize posMap
     */
    private void loadMeta() {
        for (Map.Entry<String, TableTemplate> entry : template.getTableTemplateMap().entrySet()) {
            TableTemplate tableTemplate = entry.getValue();

            List<String> insertFields = tableTemplate.getOpTypeFieldSetMap().get(OpType.ADD);
            List<String> updateFields = tableTemplate.getOpTypeFieldSetMap().get(OpType.UPDATE);
            List<String> deleteFields = tableTemplate.getOpTypeFieldSetMap().get(OpType.DELETE);

            jdbcTemplate.query(
                    SQL_SCHEMA,
                    new Object[]{template.getDatabase(), tableTemplate.getTableName()},
                    (rs, i) -> {
                        int pos = rs.getInt("ORDINAL_POSITION");
                        String colName = rs.getString("COLUMN_NAME");

                        if ((insertFields != null && insertFields.contains(colName)) ||
                                (updateFields != null && insertFields.contains(colName)) ||
                                (deleteFields != null && deleteFields.contains(colName))) {
                            tableTemplate.getPosMap().put(pos - 1, colName);
                        }
                        return null;
                    });
        }
    }
}
