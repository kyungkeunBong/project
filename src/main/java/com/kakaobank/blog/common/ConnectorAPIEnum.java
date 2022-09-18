package com.kakaobank.blog.common;

public enum ConnectorAPIEnum {
	BLOG_SEARCH("/v2/search/blog"),
	NAVER_BLOG_SEARCH("/v1/search/blog.json");	
	private String url;
	private ConnectorAPIEnum(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
}
