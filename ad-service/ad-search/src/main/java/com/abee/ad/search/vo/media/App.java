package com.abee.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xincong yao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class App {

    private String appCode;

    private String appName;

    /**
     * Android or Apple or ...
     */
    private String packageName;

    /**
     * original page of app
     */
    private String activityName;
}
