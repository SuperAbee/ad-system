package com.abee.ad.mysql;

import com.abee.ad.mysql.listener.AggregationListener;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author xincong yao
 */
@Slf4j
@Component
public class BinlogClient {

    private BinaryLogClient client;

    private final BinlogConfig config;
    private final AggregationListener aggregationListener;

    @Autowired
    public BinlogClient(BinlogConfig config, AggregationListener aggregationListener) {
        this.config = config;
        this.aggregationListener = aggregationListener;
    }

    public void connect() {
        new Thread(() -> {
            client = new BinaryLogClient(
                    config.getHost(),
                    config.getPort(),
                    config.getUsername(),
                    config.getPassword()
            );
            if (!StringUtils.isEmpty(config.getBinlogName()) &&
                    !config.getPosition().equals(-1L)) {
                client.setBinlogFilename(config.getBinlogName());
                client.setBinlogPosition(config.getPosition());
            }
            client.registerEventListener(aggregationListener);

            try {
                log.info("connecting to mysql");
                client.connect();
                log.info("connected to mysql");
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }).start();
    }

    public void close() {
        try {
            client.disconnect();
        } catch (IOException e) {
            log.error("binlog client close error");
            throw new RuntimeException(e.getMessage());
        }
    }
}
