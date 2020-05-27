package com.abee.ad.service;

import com.abee.ad.exception.AdException;
import com.abee.ad.vo.UserPostRequest;
import com.abee.ad.vo.UserPostResponse;

/**
 * @author xincong yao
 */
public interface IUserService {

    UserPostResponse createUser(UserPostRequest request) throws AdException;
}
