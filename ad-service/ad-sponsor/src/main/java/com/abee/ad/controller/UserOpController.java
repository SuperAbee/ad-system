package com.abee.ad.controller;

import com.abee.ad.exception.AdException;
import com.abee.ad.service.IUserService;
import com.abee.ad.service.impl.UserServiceImpl;
import com.abee.ad.vo.UserPostRequest;
import com.abee.ad.vo.UserPostResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xincong yao
 */
@Slf4j
@RestController
public class UserOpController {

    private final IUserService userService;

    @Autowired
    public UserOpController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public UserPostResponse createUser(
            @RequestBody UserPostRequest request) throws AdException {
        log.info("ad-sponsor: createUser -> {}", JSON.toJSONString(request));
        return userService.createUser(request);
    }
}
