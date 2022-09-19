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
	
	@RequestMapping(method = RequestMethod.GET, value = ServiceUris.BLOG_SEARCH)
	public BlogResponseVO searchBlog(@RequestParam(required = true) String query, 
			@RequestParam(required = false) String sort, 
			@RequestParam(required = false) String page,
			@RequestParam(required = false) String size,
			HttpServletRequest httpRequest) throws CommonException , ParseException, Exception{
		
		BlogResponseVO responseVO = blogService.searchBlog(setRequest(query, sort, page, size, httpRequest));
		System.out.println("## response : " + responseVO.toString());
		return responseVO;
	}

	private BlogRequestVO setRequest(String query, String sort, String page, String size,
			HttpServletRequest httpRequest) {
		BlogRequestVO request = new BlogRequestVO();
		request.setRestApiKey(httpRequest.getHeader(HeaderValues.HEADER_KEY).toString());
		
		if(httpRequest.getHeader(RestApiHost.NAVER_ID) != null) {
			// 강제 네이버 조회
			request.setNaverId(httpRequest.getHeader(RestApiHost.NAVER_ID).toString());
		}
		// requestParam 으로 들어온값 세팅
		if(query != null) {
			request.setQuery(query);
		}
		if(sort != null) {
			request.setSort(sort);
		}
		if(page != null) {
			request.setPage(Integer.parseInt(page));
		}
		if(size !=null ) {
			request.setSize(Integer.parseInt(size));
		}
		return request;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = ServiceUris.KEYWORD_TOPTEN)
	public KeywordResponseVO keywordTop() {
		System.out.println("############# start keywordTop");
		KeywordResponseVO responseVO = blogService.keywordTop();
		return responseVO;
	}
}
