package com.interceptor;

import common.Constants;
import common.ErrorCodeEnum;
import exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HeaderInterCeptor implements HandlerInterceptor{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws CommonException, IOException {
        LOGGER.debug("Interceptor start");
    	// 헤더 restApiKey 체크
        try{
            final String restApiKey = request.getHeader(Constants.HeaderValues.HEADER_KEY).split(Constants.HeaderValues.HEADER_KEY_PREVALUE)[1];
            if( restApiKey != null){
                return true;
            }
        }catch(ArrayIndexOutOfBoundsException e){
            response.sendError(401, ErrorCodeEnum.KAKAO_REST_API_KEY_ERROR.getDescription());
            response.setStatus(401);
            return false;
        }catch(NullPointerException e){
            response.sendError(401, ErrorCodeEnum.KAKAO_REST_API_KEY_ERROR.getDescription());
            response.setStatus(401);
            return false;
        }
        response.sendError(401, ErrorCodeEnum.KAKAO_REST_API_KEY_ERROR.getDescription());
        response.setStatus(401);
        return false;
    }
}
