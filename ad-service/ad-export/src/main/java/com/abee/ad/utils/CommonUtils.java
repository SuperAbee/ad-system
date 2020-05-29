package com.abee.ad.utils;

import com.abee.ad.dump.table.AdPlanTable;
import com.abee.ad.dump.table.AdUnitTable;
import com.abee.ad.entity.AdPlan;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author xincong yao
 */
@Slf4j
public class CommonUtils {

    public static <T> boolean toFile(String filename, List<T> source) {
        Path path = Paths.get(filename);
        BufferedWriter writer = null;
        try {
            writer = Files.newBufferedWriter(path);
            for (T t : source) {
                writer.write(JSON.toJSONString(t));
                writer.newLine();
            }
        } catch (IOException e) {
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    log.error(filename + " close error");
                }
            }
        }

        return true;
    }
}
