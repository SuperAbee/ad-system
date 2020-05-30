package com.abee.ad.mysql.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
 * @author xincong yao
 */
public enum OpType {

    ADD,
    DELETE,
    UPDATE,
    OTHER;

    public static OpType parse(EventType type) {
        if (type == EventType.EXT_DELETE_ROWS) {
            return DELETE;
        }
        if (type == EventType.EXT_UPDATE_ROWS) {
            return UPDATE;
        }
        if (type == EventType.EXT_WRITE_ROWS) {
            return ADD;
        }
        return OTHER;
    }
}
