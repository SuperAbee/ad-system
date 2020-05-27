package com.abee.ad.service.impl;

import com.abee.ad.constant.Constants;
import com.abee.ad.dao.AdUserRepository;
import com.abee.ad.dao.CreativeRepository;
import com.abee.ad.entity.Creative;
import com.abee.ad.exception.AdException;
import com.abee.ad.service.ICreativeService;
import com.abee.ad.vo.CreativeRequest;
import com.abee.ad.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xincong yao
 */
@Service
public class CreativeServiceImpl implements ICreativeService {

    private final CreativeRepository creativeRepository;
    private final AdUserRepository userRepository;

    @Autowired
    public CreativeServiceImpl(
            CreativeRepository creativeRepository, AdUserRepository userRepository) {
        this.userRepository = userRepository;
        this.creativeRepository = creativeRepository;
    }

    @Override
    public CreativeResponse createCreative(CreativeRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        if (!userRepository.existsById(request.getUserId())) {
            throw new AdException(Constants.ErrorMsg.RECORD_NOT_FOUND);
        }

        if (creativeRepository.existsByName(request.getName())) {
            throw new AdException(Constants.ErrorMsg.SAME_CREATIVE_NAME_ERROR);
        }

        Creative creative = creativeRepository.save(new Creative(request));

        return new CreativeResponse(creative);
    }
}
