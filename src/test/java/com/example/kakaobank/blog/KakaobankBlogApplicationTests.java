package com.example.kakaobank.blog;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import com.kakaobank.blog.KakaobankBlogApplication;
import com.kakaobank.blog.common.Constants.HeaderValues;
import com.kakaobank.blog.common.Constants.RestApiHost;
import com.kakaobank.blog.common.Constants.RestApiKeys;
import com.kakaobank.blog.common.Constants.ServiceUris;
import com.kakaobank.blog.vo.req.BlogRequestVO;
import com.kakaobank.blog.vo.res.BlogResponseVO;
import com.kakaobank.blog.vo.res.KeywordResponseVO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = KakaobankBlogApplication.class)
class KakaobankBlogApplicationTests {

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
	@DisplayName("카카오 blog 검색 최근조회 테스트")
	@Test
	void 카카오_blog_검색_recency_테스트() throws URISyntaxException {
		URI uri = new URI(baseurl+ ServiceUris.BLOG_SEARCH);
		
		BlogRequestVO requestVO = new BlogRequestVO();
		requestVO.setQuery("test");
		requestVO.setSort("recency");
				
		HttpHeaders header = kakaoHeader();
		HttpEntity<BlogRequestVO> request = new HttpEntity<>(requestVO, header);
		
		
		//when
		ResponseEntity<BlogResponseVO> response = 
				restTemplate.postForEntity(uri.toString(), request, BlogResponseVO.class);
		
		//then
		assertNotNull(response.getBody());
		
	}
	
	@DisplayName("카카오 blog 검색 정확도 조회 테스트")
	@Test
	void 카카오_blog_검색_accuracy_테스트() throws URISyntaxException {
		URI uri = new URI(baseurl+ ServiceUris.BLOG_SEARCH);
		
		BlogRequestVO requestVO = new BlogRequestVO();
		requestVO.setQuery("test");
//		requestVO.setSort("accuracy"); // 디폴트 세팅됨
				
		HttpHeaders header = kakaoHeader();
		HttpEntity<BlogRequestVO> request = new HttpEntity<>(requestVO, header);
		
		
		//when
		ResponseEntity<BlogResponseVO> response = 
				restTemplate.postForEntity(uri.toString(), request, BlogResponseVO.class);
		
		//then
		assertNotNull(response.getBody());
		
	}
	
	@DisplayName("네이버 blog 검색 테스트")
	@Test
	void 네이버_blog_검색_테스트() throws URISyntaxException {
		URI uri = new URI(baseurl+ ServiceUris.BLOG_SEARCH);
		
		BlogRequestVO requestVO = new BlogRequestVO();
		requestVO.setQuery("test");
		
		HttpHeaders header = naverHeader();
		HttpEntity<BlogRequestVO> request = new HttpEntity<>(requestVO, header);
		
		//when
		ResponseEntity<BlogResponseVO> response = 
				restTemplate.postForEntity(uri.toString(), request, BlogResponseVO.class);
		//then
		assertNotNull(response.getBody());
		
	}
	
	@DisplayName("인기검색어 목록 테스트")
	@Test
	void 인기검색어_목록_테스트() throws URISyntaxException {
		URI uri = new URI(baseurl+ ServiceUris.KEYWORD_TOPTEN);
		
		ResponseEntity<KeywordResponseVO> response = 
				restTemplate.getForEntity(uri.toString(), KeywordResponseVO.class);
				
		System.out.println("#############" + response.toString()); 	
	}


}
