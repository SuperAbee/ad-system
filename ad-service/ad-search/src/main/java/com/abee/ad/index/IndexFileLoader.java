package com.abee.ad.index;

import com.abee.ad.dump.DConstant;
import com.abee.ad.dump.table.*;
import com.abee.ad.handler.AdLevelDataHandler;
import com.abee.ad.mysql.constant.OpType;
import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xincong yao
 */
@DependsOn("dataTable")
@Component
public class IndexFileLoader {

    @PostConstruct
    public void init() {
        /**
         * Index level 2
         */
        List<String> rowPlans = loadDumpData(DConstant.DATA_ROOT_DIR + DConstant.AD_PLAN);
        rowPlans.forEach(p ->
                AdLevelDataHandler.handleLevel2(
                        JSON.parseObject(p, AdPlanTable.class), OpType.ADD
                )
        );

        List<String> rowCreative = loadDumpData(DConstant.DATA_ROOT_DIR + DConstant.AD_CREATIVE);
        rowCreative.forEach(c ->
                AdLevelDataHandler.handleLevel2(
                        JSON.parseObject(c, CreativeTable.class), OpType.ADD
                )
        );

        /**
         * Index level 3
         */
        List<String> rowUnits = loadDumpData(DConstant.DATA_ROOT_DIR + DConstant.AD_UNIT);
        rowUnits.forEach(u ->
                AdLevelDataHandler.handleLevel3(
                        JSON.parseObject(u, AdUnitTable.class), OpType.ADD
                )
        );

        List<String> rowCreativeUnits = loadDumpData(DConstant.DATA_ROOT_DIR + DConstant.AD_CREATIVE_UNIT);
        rowCreativeUnits.forEach(u ->
                AdLevelDataHandler.handleLevel3(
                        JSON.parseObject(u, CreativeUnitTable.class), OpType.ADD
                )
        );

        /**
         * Index level 4
         */
        List<String> rowUnitIt = loadDumpData(DConstant.DATA_ROOT_DIR + DConstant.AD_UNIT_IT);
        rowUnitIt.forEach(u ->
                AdLevelDataHandler.handleLevel4(
                        JSON.parseObject(u, AdUnitItTable.class), OpType.ADD
                )
        );

        List<String> rowUnitKeyword = loadDumpData(DConstant.DATA_ROOT_DIR + DConstant.AD_UNIT_KEYWORD);
        rowUnitKeyword.forEach(u ->
                AdLevelDataHandler.handleLevel4(
                        JSON.parseObject(u, AdUnitKeywordTable.class), OpType.ADD
                )
        );

        List<String> rowUnitDistricts = loadDumpData(DConstant.DATA_ROOT_DIR + DConstant.AD_UNIT_DISTRICT);
        rowUnitDistricts.forEach(u ->
                AdLevelDataHandler.handleLevel4(
                        JSON.parseObject(u, AdUnitDistrictTable.class), OpType.ADD
                )
        );
    }

    private List<String> loadDumpData(String filename) {
        Path path = Paths.get(filename);
        BufferedReader br = null;
        try {
            br = Files.newBufferedReader(path);
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
    }
}
