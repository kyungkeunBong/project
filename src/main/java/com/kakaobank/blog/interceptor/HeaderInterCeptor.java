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
		String apiKey = "";
		if(request.getHeader(HeaderValues.HEADER_KEY) != null){
			apiKey = request.getHeader(HeaderValues.HEADER_KEY).split(HeaderValues.HEADER_KEY_PREVALUE)[1];
		}else {
			// 테스트용 임시코드
			apiKey = RestApiKeys.KAKAO_REST_API_KEY;
		}
		
        if (apiKey != null) {
        	System.out.println("Header Input HEADER_AUTH : " + apiKey);
        	request.setAttribute(HeaderValues.HEADER_KEY, RestApiKeys.KAKAO_REST_API_KEY);
            return true;
        }else {
          response.sendError(401,ErrorCodeEnum.KAKAO_REST_API_KEY_ERROR.getDescription());
          response.setStatus(401);
          return false;
        }
    }

}
