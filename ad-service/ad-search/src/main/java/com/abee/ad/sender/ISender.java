package com.abee.ad.sender;

import com.abee.ad.mysql.dto.MySqlRowData;

/**
 * @author xincong yao
 */
public interface ISender {

    void send(MySqlRowData data);
}
