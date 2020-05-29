package com.abee.ad.service;

import com.abee.ad.ExportApplicationTest;
import com.abee.ad.dump.DConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xincong yao
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExportApplicationTest.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpDataServiceTest {

    @Autowired
    private DataDumpService dataDumpService;

    @Test
    public void dumpTableData() {
        dataDumpService.dumpAdPlanTable(DConstant.DATA_ROOT_DIR + DConstant.AD_PLAN);
        dataDumpService.dumpAdUnitTable(DConstant.DATA_ROOT_DIR + DConstant.AD_UNIT);
        dataDumpService.dumpCreativeTable(DConstant.DATA_ROOT_DIR + DConstant.AD_CREATIVE);
        dataDumpService.dumpCreativeUnitTable(DConstant.DATA_ROOT_DIR + DConstant.AD_CREATIVE_UNIT);
        dataDumpService.dumpUnitItTable(DConstant.DATA_ROOT_DIR + DConstant.AD_UNIT_IT);
        dataDumpService.dumpUnitKeywordTable(DConstant.DATA_ROOT_DIR + DConstant.AD_UNIT_KEYWORD);
        dataDumpService.dumpUnitDistrictTable(DConstant.DATA_ROOT_DIR + DConstant.AD_UNIT_DISTRICT);
    }
}
