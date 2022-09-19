package com.kakaobank.blog.common;

import com.kakaobank.blog.common.Constants.RestApiKeys;

public class Constants {
	public static class ServiceUris{
		public static final String BLOG_SEARCH = "/rest/blog-service/blog/search/list";
		public static final String KEYWORD_TOPTEN = "/rest/blog-service/keyword/top";
	}
	public static class HeaderValues{
		public static final String HEADER_KEY = "Authorization";
		public static final String HEADER_KEY_PREVALUE = "KakaoAK ";
	}
	public static class RestApiHost{
		public static final String KAKAO_HOST = "https://dapi.kakao.com";
		public static final String NAVER_HOST = "https://openapi.naver.com";
		public static final String NAVER_ID = "X-Naver-Client-Id";
		public static final String NAVER_SERET = "X-Naver-Client-Secret";
		
	}
	public static class RestApiKeys{
		public static final String KAKAO_REST_API_KEY = "KakaoAK bab1235ec50b97cb58049b4633a7849b";
		public static final String NAVER_CLIENT_ID = "aSfBrdAq9j0pNd40Igwy";
		public static final String NAVER_CLIENT_SECRET = "beKmWfKWcv";
	}
}
