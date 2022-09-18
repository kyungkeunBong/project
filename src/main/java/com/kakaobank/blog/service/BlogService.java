package com.kakaobank.blog.service;

import java.text.ParseException;

import com.kakaobank.blog.exception.CommonException;
import com.kakaobank.blog.vo.req.BlogRequestVO;
import com.kakaobank.blog.vo.res.BlogResponseVO;

public interface BlogService {
	BlogResponseVO searchBlog(BlogRequestVO requestBody) throws CommonException, ParseException, Exception; 
}
