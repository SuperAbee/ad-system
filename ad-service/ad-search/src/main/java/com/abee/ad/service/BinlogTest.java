package com.abee.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

import java.io.IOException;

public class BinlogTest {

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
