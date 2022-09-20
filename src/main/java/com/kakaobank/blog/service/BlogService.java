package com.kakaobank.blog.service;

import java.text.ParseException;

import exception.CommonException;
import com.kakaobank.blog.vo.req.BlogRequestVO;
import com.kakaobank.blog.vo.res.BlogResponseVO;
import com.kakaobank.blog.vo.res.KeywordResponseVO;

public interface BlogService {
	BlogResponseVO searchBlog(BlogRequestVO requestBody) throws CommonException, ParseException, Exception;
	KeywordResponseVO keywordTop();
}
