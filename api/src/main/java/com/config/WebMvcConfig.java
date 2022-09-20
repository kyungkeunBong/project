package com.config;

import com.interceptor.HeaderInterCeptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Autowired
	HeaderInterCeptor headerInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(headerInterceptor)
		.addPathPatterns("/rest/blog-com.service/**")
		.excludePathPatterns("/rest/blog-com.service/keyword/top");
	}
}
