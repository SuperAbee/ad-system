package com.abee.ad.sender.index;

import com.abee.ad.dump.table.*;
import com.abee.ad.handler.AdLevelDataHandler;
import com.abee.ad.index.DataLevel;
import com.abee.ad.mysql.constant.Constants;
import com.abee.ad.mysql.dto.MySqlRowData;
import com.abee.ad.sender.ISender;
import com.abee.ad.utils.CommonUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xincong yao
 */
@Slf4j
@Component("indexSender")
public class IndexSender implements ISender {

    /**
     * @core
     *      in level2/3/4/RowData()
     *      list.forEach(e -> AdLevelDataHandler.handleLevel2/3/4(e, rowData.getType()));
     */
    @Override
    public void send(MySqlRowData data) {
        String level = data.getLevel();

        if (DataLevel.LEVEL2.getLevel().equals(level)) {
            level2RowData(data);
        } else if (DataLevel.LEVEL3.getLevel().equals(level)) {
            level3RowData(data);
        } else if (DataLevel.LEVEL4.getLevel().equals(level)) {
            level4RowData(data);
        } else {
            log.error("MySqlRowData error: {}", JSON.toJSONString(data));
        }
    }

    private void level2RowData(MySqlRowData rowData) {
        if (rowData.getTableName().equals(
                Constants.AD_PLAN_TABLE_INFO.TABLE_NAME)) {
            List<AdPlanTable> planList = new ArrayList<>();

            for (Map<String, String> field : rowData.getFieldValueMap()) {
                AdPlanTable planTable = new AdPlanTable();
                for (Map.Entry<String, String> e : field.entrySet()) {
                    switch (e.getKey()) {
                        case Constants.AD_PLAN_TABLE_INFO.COLUMN_ID:
                            planTable.setPlanId(Long.valueOf(e.getKey()));
                            break;
                        case Constants.AD_PLAN_TABLE_INFO.COLUMN_USER_ID:
                            planTable.setUserId(Long.valueOf(e.getKey()));
                            break;
                        case Constants.AD_PLAN_TABLE_INFO.COLUMN_PLAN_STATUS:
                            planTable.setPlanStatus(Integer.valueOf(e.getKey()));
                            break;
                        case Constants.AD_PLAN_TABLE_INFO.COLUMN_START_TIME:
                            planTable.setStartTime(CommonUtils.parseStringDateFromBinlog(e.getKey()));
                            break;
                        case Constants.AD_PLAN_TABLE_INFO.COLUMN_END_TIME:
                            planTable.setEndTime(CommonUtils.parseStringDateFromBinlog(e.getKey()));
                            break;
                        default:
                            break;
                    }
                }
                planList.add(planTable);
            }

            planList.forEach(e -> AdLevelDataHandler.handleLevel2(e, rowData.getType()));
        } else if (rowData.getTableName().equals(
                Constants.AD_CREATIVE_TABLE_INFO.TABLE_NAME)) {
            List<CreativeTable> creativeList = new ArrayList<>();
            for (Map<String, String> field : rowData.getFieldValueMap()) {
                CreativeTable creativeTable = new CreativeTable();
                for (Map.Entry<String, String> e : field.entrySet()) {
                    switch (e.getKey()) {
                        case Constants.AD_CREATIVE_TABLE_INFO.COLUMN_ID:
                            creativeTable.setCreativeId(Long.valueOf(e.getKey()));
                            break;
                        case Constants.AD_CREATIVE_TABLE_INFO.COLUMN_AUDIT_STATUS:
                            creativeTable.setAuditStatus(Integer.valueOf(e.getKey()));
                            break;
                        case Constants.AD_CREATIVE_TABLE_INFO.COLUMN_HEIGHT:
                            creativeTable.setHeight(Integer.valueOf(e.getKey()));
                            break;
                        case Constants.AD_CREATIVE_TABLE_INFO.COLUMN_WIDTH:
                            creativeTable.setWidth(Integer.valueOf(e.getKey()));
                            break;
                        case Constants.AD_CREATIVE_TABLE_INFO.COLUMN_TYPE:
                            creativeTable.setType(Integer.valueOf(e.getKey()));
                            break;
                        case Constants.AD_CREATIVE_TABLE_INFO.COLUMN_MATERIAL_TYPE:
                            creativeTable.setMaterialType(Integer.valueOf(e.getKey()));
                            break;
                        case Constants.AD_CREATIVE_TABLE_INFO.COLUMN_URL:
                            creativeTable.setUrl(e.getKey());
                            break;
                        default:
                            break;
                    }
                }
                creativeList.add(creativeTable);
            }
            creativeList.forEach(e -> AdLevelDataHandler.handleLevel2(e, rowData.getType()));
        }
    }

    private void level3RowData(MySqlRowData rowData) {
        if (rowData.getTableName().equals(
                Constants.AD_UNIT_TABLE_INFO.TABLE_NAME)) {

            List<AdUnitTable> unitTables = new ArrayList<>();

            for (Map<String, String> fieldValueMap :
                    rowData.getFieldValueMap()) {

                AdUnitTable unitTable = new AdUnitTable();

                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constants.AD_UNIT_TABLE_INFO.COLUMN_ID:
                            unitTable.setUnitId(Long.valueOf(v));
                            break;
                        case Constants.AD_UNIT_TABLE_INFO.COLUMN_UNIT_STATUS:
                            unitTable.setUnitStatus(Integer.valueOf(v));
                            break;
                        case Constants.AD_UNIT_TABLE_INFO.COLUMN_POSITION_TYPE:
                            unitTable.setPositionType(Integer.valueOf(v));
                            break;
                        case Constants.AD_UNIT_TABLE_INFO.COLUMN_PLAN_ID:
                            unitTable.setPlanId(Long.valueOf(v));
                            break;
                    }
                });

                unitTables.add(unitTable);
            }

            unitTables.forEach(u ->
                    AdLevelDataHandler.handleLevel3(u, rowData.getType()));
        } else if (rowData.getTableName().equals(
                Constants.CREATIVE_UNIT_TABLE_INFO.TABLE_NAME
        )) {
            List<CreativeUnitTable> creativeUnitTables = new ArrayList<>();

            for (Map<String, String> fieldValueMap :
                    rowData.getFieldValueMap()) {

                CreativeUnitTable creativeUnitTable = new CreativeUnitTable();

                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constants.CREATIVE_UNIT_TABLE_INFO.COLUMN_CREATIVE_ID:
                            creativeUnitTable.setCreativeId(Long.valueOf(v));
                            break;
                        case Constants.CREATIVE_UNIT_TABLE_INFO.COLUMN_UNIT_ID:
                            creativeUnitTable.setUnitId(Long.valueOf(v));
                            break;
                    }
                });

                creativeUnitTables.add(creativeUnitTable);
            }

            creativeUnitTables.forEach(
                    u -> AdLevelDataHandler.handleLevel3(u, rowData.getType())
            );
        }
    }

    private void level4RowData(MySqlRowData rowData) {
        switch (rowData.getTableName()) {
            case Constants.AD_UNIT_DISTRICT_TABLE_INFO.TABLE_NAME:
                List<AdUnitDistrictTable> districtTables = new ArrayList<>();

                for (Map<String, String> fieldValueMap :
                        rowData.getFieldValueMap()) {

                    AdUnitDistrictTable districtTable = new AdUnitDistrictTable();

                    fieldValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constants.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_UNIT_ID:
                                districtTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constants.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_PROVINCE:
                                districtTable.setProvince(v);
                                break;
                            case Constants.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_CITY:
                                districtTable.setCity(v);
                                break;
                        }
                    });

                    districtTables.add(districtTable);
                }

                districtTables.forEach(
                        d -> AdLevelDataHandler.handleLevel4(d, rowData.getType())
                );
                break;
            case Constants.AD_UNIT_IT_TABLE_INFO.TABLE_NAME:
                List<AdUnitItTable> itTables = new ArrayList<>();

                for (Map<String, String> fieldValueMap :
                        rowData.getFieldValueMap()) {

                    AdUnitItTable itTable = new AdUnitItTable();

                    fieldValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constants.AD_UNIT_IT_TABLE_INFO.COLUMN_UNIT_ID:
                                itTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constants.AD_UNIT_IT_TABLE_INFO.COLUMN_IT_TAG:
                                itTable.setItTag(v);
                                break;
                        }
                    });
                    itTables.add(itTable);
                }
                itTables.forEach(
                        i -> AdLevelDataHandler.handleLevel4(i, rowData.getType())
                );
                break;
            case Constants.AD_UNIT_KEYWORD_TABLE_INFO.TABLE_NAME:

                List<AdUnitKeywordTable> keywordTables = new ArrayList<>();

                for (Map<String, String> fieldValueMap :
                        rowData.getFieldValueMap()) {
                    AdUnitKeywordTable keywordTable = new AdUnitKeywordTable();

                    fieldValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constants.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_UNIT_ID:
                                keywordTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constants.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_KEYWORD:
                                keywordTable.setKeyword(v);
                                break;
                        }
                    });
                    keywordTables.add(keywordTable);
                }

                keywordTables.forEach(
                        k -> AdLevelDataHandler.handleLevel4(k, rowData.getType())
                );
                break;
        }
    }
}
