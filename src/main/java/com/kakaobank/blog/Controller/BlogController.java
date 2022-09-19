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
		
		BlogRequestVO request = new BlogRequestVO();
		request.setRestApiKey(httpRequest.getAttribute(HeaderValues.HEADER_KEY).toString());
		
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
		
		BlogResponseVO responseVO = blogService.searchBlog(request);
		System.out.println("## response : " + responseVO.toString());
		return responseVO;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = ServiceUris.KEYWORD_TOPTEN)
	public KeywordResponseVO keywordTop() {
		KeywordResponseVO responseVO = blogService.keywordTop();
		return responseVO;
	}
}
