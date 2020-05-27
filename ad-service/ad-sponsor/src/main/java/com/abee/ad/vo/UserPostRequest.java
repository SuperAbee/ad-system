package com.abee.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @author xincong yoa
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPostRequest {

    private String username;

    public boolean createValidate() {
        return !StringUtils.isEmpty(username);
    }
}
