package com.service;

import exception.CommonException;
import com.vo.req.BlogRequestVO;
import com.vo.res.BlogResponseVO;
import com.vo.res.KeywordResponseVO;

import java.text.ParseException;

public interface BlogService {
	BlogResponseVO searchBlog(BlogRequestVO requestBody) throws CommonException, ParseException, Exception;
	KeywordResponseVO keywordTop();
}
