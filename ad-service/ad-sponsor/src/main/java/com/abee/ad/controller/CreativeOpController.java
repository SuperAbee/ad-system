package com.abee.ad.controller;

import com.abee.ad.exception.AdException;
import com.abee.ad.service.ICreativeService;
import com.abee.ad.service.impl.CreativeServiceImpl;
import com.abee.ad.vo.CreativeRequest;
import com.abee.ad.vo.CreativeResponse;
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
public class CreativeOpController {

    private final ICreativeService creativeService;

    @Autowired
    public CreativeOpController(CreativeServiceImpl creativeService) {
        this.creativeService = creativeService;
    }

    @PostMapping("/creative")
    public CreativeResponse createCreative(
            @RequestBody CreativeRequest request) throws AdException {
        log.info("ad-sponsor: createCreative -> {}", JSON.toJSONString(request));
        return creativeService.createCreative(request);
    }
}
