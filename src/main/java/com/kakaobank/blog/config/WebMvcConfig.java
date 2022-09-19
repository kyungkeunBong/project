package com.kakaobank.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kakaobank.blog.interceptor.HeaderInterCeptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Autowired
	HeaderInterCeptor headerInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(headerInterceptor)
		.addPathPatterns("/rest/blog-service/**")
		.excludePathPatterns("/rest/blog-service/keyword/*");				
	}
}
