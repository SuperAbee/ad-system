package com.abee.ad.advice;

import com.abee.ad.exception.AdException;
import com.abee.ad.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xincong yao
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler
    public CommonResponse<String> handleAdException(HttpServletRequest request,
                                                    AdException e) {
        CommonResponse<String> response = new CommonResponse<>(-1,
                "Error");
        response.setData(e.getMessage());

        return response;
    }
}
