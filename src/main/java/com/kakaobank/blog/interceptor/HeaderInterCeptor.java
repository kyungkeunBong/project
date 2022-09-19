package com.kakaobank.blog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kakaobank.blog.common.Constants.HeaderValues;
import com.kakaobank.blog.common.Constants.RestApiKeys;
import com.kakaobank.blog.common.ErrorCodeEnum;
import com.kakaobank.blog.exception.CommonException;

@Component
public class HeaderInterCeptor implements HandlerInterceptor{
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws CommonException, Exception {
    	// 헤더 restApiKey 체크
		System.out.println("##### interceptror start ");
        if (request.getHeader(HeaderValues.HEADER_KEY).split(HeaderValues.HEADER_KEY_PREVALUE)[1] != null) {
        	return true;
        }else {
          response.sendError(401,ErrorCodeEnum.KAKAO_REST_API_KEY_ERROR.getDescription());
          response.setStatus(401);
          return false;
        }
    }

}
