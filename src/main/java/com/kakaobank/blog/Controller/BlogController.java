package com.kakaobank.blog.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kakaobank.blog.common.Constants.HeaderValues;
import com.kakaobank.blog.common.Constants.RestApiHost;
import com.kakaobank.blog.common.Constants.ServiceUris;
import com.kakaobank.blog.exception.CommonException;
import com.kakaobank.blog.service.BlogService;
import com.kakaobank.blog.vo.req.BlogRequestVO;
import com.kakaobank.blog.vo.res.BlogResponseVO;
import com.kakaobank.blog.vo.res.KeywordResponseVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BlogController {
	private final BlogService blogService;
	
	@RequestMapping(method = RequestMethod.POST, value = ServiceUris.BLOG_SEARCH)
	public BlogResponseVO searchBlog(@RequestBody BlogRequestVO requestBody,
			HttpServletRequest httpRequest) throws CommonException , ParseException, Exception{
		requestBody.setRestApiKey(httpRequest.getHeader(HeaderValues.HEADER_KEY).toString());
		if(httpRequest.getHeader(RestApiHost.NAVER_ID) != null) {
			// 강제 네이버 조회
			requestBody.setNaverId(httpRequest.getHeader(RestApiHost.NAVER_ID).toString());
		}
		BlogResponseVO responseVO = blogService.searchBlog(requestBody);
		System.out.println("## response : " + responseVO.toString());
		return responseVO;
	}

	
	@RequestMapping(method = RequestMethod.GET, value = ServiceUris.KEYWORD_TOPTEN)
	public KeywordResponseVO keywordTop() {
		System.out.println("############# start keywordTop");
		KeywordResponseVO responseVO = blogService.keywordTop();
		return responseVO;
	}
}
