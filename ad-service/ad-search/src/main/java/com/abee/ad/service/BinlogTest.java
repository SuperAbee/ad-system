package com.abee.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

import java.io.IOException;

public class BinlogTest {

    /**
     * WriteRowsEventData{tableId=101, includedColumns={0, 1, 2, 3, 4, 5, 6, 7}, rows=[
     *     [10, 1, plan1, 1, Tue Mar 03 08:00:00 CST 2020, Tue Mar 03 08:00:00 CST 2020, Thu Jan 01 08:00:00 CST 1970, Thu Jan 01 08:00:00 CST 1970]
     * ]}
     *
     * Tue Mar 03 08:00:00 CST 2020
     */

    public static void main(String[] args) throws IOException {
        BinaryLogClient bc = new BinaryLogClient(
                "127.0.0.1", 3306,
                "root", "password");
        //bc.setBinlogFilename();
        //bc.setBinlogPosition();

        bc.registerEventListener(event -> {
            EventData data = event.getData();

            if (data instanceof UpdateRowsEventData) {
                System.out.println("Update-------");
                System.out.println(data.toString());
                System.out.println("Update---------");
            } else if (data instanceof DeleteRowsEventData) {
                System.out.println("Delete-------");
                System.out.println(data.toString());
                System.out.println("Delete---------");
            } else if (data instanceof WriteRowsEventData) {
                System.out.println("Write-------");
                System.out.println(data.toString());
                System.out.println("Write---------");
            }
        });

        bc.connect();
    }
}
