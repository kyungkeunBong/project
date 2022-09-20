package com.kakaobank.blog.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import exception.CommonException;
import com.kakaobank.blog.service.BlogService;
import com.kakaobank.blog.vo.req.BlogRequestVO;
import com.kakaobank.blog.vo.res.BlogResponseVO;
import com.kakaobank.blog.vo.res.KeywordResponseVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BlogController {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private final BlogService blogService;
	
	@RequestMapping(method = RequestMethod.POST, value = Constants.ServiceUris.BLOG_SEARCH)
	public BlogResponseVO searchBlog(@RequestBody BlogRequestVO requestBody,
			HttpServletRequest httpRequest) throws CommonException , ParseException, Exception{
		requestBody.setRestApiKey(httpRequest.getHeader(Constants.HeaderValues.HEADER_KEY).toString());
		if(httpRequest.getHeader(Constants.RestApiHost.NAVER_ID) != null) {
			// 강제 네이버 조회
			requestBody.setNaverId(httpRequest.getHeader(Constants.RestApiHost.NAVER_ID).toString());
		}
		BlogResponseVO responseVO = blogService.searchBlog(requestBody);
		return responseVO;
	}

	
	@RequestMapping(method = RequestMethod.GET, value = Constants.ServiceUris.KEYWORD_TOPTEN)
	public KeywordResponseVO keywordTop() {
		KeywordResponseVO responseVO = blogService.keywordTop();
		return responseVO;
	}
}
