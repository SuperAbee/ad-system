package com.abee.ad.service.impl;

import com.abee.ad.constant.Constants;
import com.abee.ad.dao.AdUserRepository;
import com.abee.ad.entity.AdUser;
import com.abee.ad.exception.AdException;
import com.abee.ad.service.IUserService;
import com.abee.ad.vo.UserPostRequest;
import com.abee.ad.vo.UserPostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xincong yao
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final AdUserRepository userRepository;

    @Autowired
    public UserServiceImpl(AdUserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    @Transactional
    public UserPostResponse createUser(UserPostRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdUser oldUser = userRepository.findByUsername(request.getUsername());
        if (oldUser != null) {
            throw new AdException(Constants.ErrorMsg.SAME_USER_NAME_ERROR);
        }

        AdUser newUser = userRepository.save(new AdUser(request));

        return new UserPostResponse(newUser);
    }
}
