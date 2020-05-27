package com.abee.ad.service;

import com.abee.ad.exception.AdException;
import com.abee.ad.vo.CreativeRequest;
import com.abee.ad.vo.CreativeResponse;

/**
 * @author xincong yao
 */
public interface ICreativeService {

    CreativeResponse createCreative(CreativeRequest request) throws AdException;
}
