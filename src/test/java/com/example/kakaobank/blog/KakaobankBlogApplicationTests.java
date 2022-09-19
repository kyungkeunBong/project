package com.example.kakaobank.blog;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.util.UriComponentsBuilder;

import com.kakaobank.blog.KakaobankBlogApplication;
import com.kakaobank.blog.common.Constants.HeaderValues;
import com.kakaobank.blog.common.Constants.RestApiHost;
import com.kakaobank.blog.common.Constants.RestApiKeys;
import com.kakaobank.blog.common.Constants.ServiceUris;
import com.kakaobank.blog.controller.BlogController;
import com.kakaobank.blog.vo.req.BlogRequestVO;
import com.kakaobank.blog.vo.res.BlogResponseVO;
import com.kakaobank.blog.vo.res.KeywordResponseVO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = KakaobankBlogApplication.class)
class KakaobankBlogApplicationTests {

	@Autowired
	private BlogController blogController;
	@Autowired
	TestRestTemplate restTemplate;
	String baseurl;
	
	@BeforeEach
	public void SetupTest() {
		baseurl = "http://localhost:8080";
	}
	
	HttpHeaders kakaoHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HeaderValues.HEADER_KEY, RestApiKeys.KAKAO_REST_API_KEY);
		return headers;	
	}
	
	HttpHeaders naverHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HeaderValues.HEADER_KEY, RestApiKeys.KAKAO_REST_API_KEY);
		headers.set(RestApiHost.NAVER_ID, RestApiKeys.NAVER_CLIENT_ID);
		headers.set(RestApiHost.NAVER_SERET, RestApiKeys.NAVER_CLIENT_SECRET);
		return headers;	
	}
	@DisplayName("카카오 blog 검색 테스트")
	@Test
	void 카카오_blog_검색_테스트() throws URISyntaxException {
		URI uri = new URI(baseurl+ ServiceUris.BLOG_SEARCH);
		
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri.toString())
				.queryParam("query", "test")
			    .queryParam("sort", "recency");
				
		HttpHeaders header = kakaoHeader();
		HttpEntity<BlogRequestVO> request = new HttpEntity<BlogRequestVO>(header);
		
		System.out.println("URL : " +uriBuilder.toUriString());
		//when
		ResponseEntity<BlogResponseVO> response = 
				restTemplate.exchange(uriBuilder.toUriString(),  HttpMethod.GET, request, BlogResponseVO.class);
				
		System.out.println(response.toString()); 	
	}
	
	@DisplayName("네이버 blog 검색 테스트")
	@Test
	void 네이버_blog_검색_테스트() throws URISyntaxException {
		URI uri = new URI(baseurl+ ServiceUris.BLOG_SEARCH);
		
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri.toString())
				.queryParam("query", "test")
			    .queryParam("sort", "recency");
				
		HttpHeaders header = naverHeader();
		HttpEntity<BlogRequestVO> request = new HttpEntity<BlogRequestVO>(header);
		
		System.out.println("URL : " +uriBuilder.toUriString());
		//when
		ResponseEntity<BlogResponseVO> response = 
				restTemplate.exchange(uriBuilder.toUriString(),  HttpMethod.GET, request, BlogResponseVO.class);
				
		System.out.println(response.toString()); 	
	}
	
	@DisplayName("인기검색어 목록 테스트")
	@Test
	void 인기검색어_목록_테스트() throws URISyntaxException {
		URI uri = new URI(baseurl+ ServiceUris.KEYWORD_TOPTEN);
						
		HttpHeaders header = naverHeader();
		//when
		ResponseEntity<KeywordResponseVO> response = 
				restTemplate.getForEntity(uri.toString(), KeywordResponseVO.class);
				
		System.out.println(response.toString()); 	
	}


}
