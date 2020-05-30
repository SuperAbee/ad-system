package com.abee.ad.mysql.listener;

import com.abee.ad.mysql.dto.BinlogRowData;

/**
 * @author xincong yao
 */
public interface IListener {

    void register();

    void onEvent(BinlogRowData rowData);

}
