package com.abee.ad.vo;

import com.abee.ad.entity.AdUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author xincong yao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPostResponse {

    private Long userId;

    private String username;

    private String token;

    private Date createTime;

    private Date updateTime;

    public UserPostResponse(AdUser user) {
        userId = user.getId();
        username = user.getUsername();
        token = user.getToken();
        createTime = user.getCreateTime();
        updateTime = user.getUpdateTime();
    }
}
