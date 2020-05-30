package com.abee.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xincong yao
 * @see DatabaseTemplate
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonDatabase {

    private String database;
    private List<JsonTable> tableList;
}
