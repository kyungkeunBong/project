package blog;

import com.KakaobankBlogApplication;
import common.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import com.vo.req.BlogRequestVO;
import com.vo.res.BlogResponseVO;
import com.vo.res.KeywordResponseVO;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = KakaobankBlogApplication.class)
class KakaobankBlogApplicationTests {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	String baseurl;
	
	@BeforeEach
	public void SetupTest() {
		baseurl = "http://localhost:8080";
	}
	
	HttpHeaders kakaoHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(Constants.HeaderValues.HEADER_KEY, Constants.RestApiKeys.KAKAO_REST_API_KEY);
		return headers;	
	}
	
	HttpHeaders naverHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(Constants.HeaderValues.HEADER_KEY, Constants.RestApiKeys.KAKAO_REST_API_KEY);
		headers.set(Constants.RestApiHost.NAVER_ID, Constants.RestApiKeys.NAVER_CLIENT_ID);
		headers.set(Constants.RestApiHost.NAVER_SERET, Constants.RestApiKeys.NAVER_CLIENT_SECRET);
		return headers;	
	}
	@DisplayName("카카오 blog 검색 최근조회 테스트")
	@Test
	void 카카오_blog_검색_recency_테스트() throws URISyntaxException {
		URI uri = new URI(baseurl+ Constants.ServiceUris.BLOG_SEARCH);
		System.out.println("uri : " + uri.toString());
		BlogRequestVO requestVO = new BlogRequestVO();
		requestVO.setQuery("test");
		requestVO.setSort("recency");
				
		HttpHeaders header = kakaoHeader();
		HttpEntity<BlogRequestVO> request = new HttpEntity<>(requestVO, header);

		//when
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<BlogResponseVO> response =
				restTemplate.postForEntity(uri.toString(), request, BlogResponseVO.class);
		
		//then
		assertNotNull(response.getBody());
		LOGGER.debug("naver search recency result {}" , response.getBody());
	}
	
	@DisplayName("카카오 blog 검색 정확도 조회 테스트")
	@Test
	void 카카오_blog_검색_accuracy_테스트() throws URISyntaxException {
		URI uri = new URI(baseurl+ Constants.ServiceUris.BLOG_SEARCH);
		
		BlogRequestVO requestVO = new BlogRequestVO();
		requestVO.setQuery("test");
//		requestVO.setSort("accuracy"); // 디폴트 세팅됨
				
		HttpHeaders header = kakaoHeader();
		HttpEntity<BlogRequestVO> request = new HttpEntity<>(requestVO, header);
		
		
		//when
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<BlogResponseVO> response = 
				restTemplate.postForEntity(uri.toString(), request, BlogResponseVO.class);
		
		//then
		assertNotNull(response.getBody());
		LOGGER.debug("kakao search accurate result {}" , response.getBody());
	}
	
	@DisplayName("네이버 blog 검색 테스트")
	@Test
	void 네이버_blog_검색_테스트() throws URISyntaxException {
		URI uri = new URI(baseurl+ Constants.ServiceUris.BLOG_SEARCH);
		
		BlogRequestVO requestVO = new BlogRequestVO();
		requestVO.setQuery("test");
		
		HttpHeaders header = naverHeader();
		HttpEntity<BlogRequestVO> request = new HttpEntity<>(requestVO, header);
		
		//when
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<BlogResponseVO> response = 
				restTemplate.postForEntity(uri.toString(), request, BlogResponseVO.class);
		//then
		assertNotNull(response.getBody());
		LOGGER.debug("naver search result {}" , response.getBody());
	}
	
	@DisplayName("인기검색어 목록 테스트")
	@Test
	void 인기검색어_목록_테스트() throws URISyntaxException {
		URI uri = new URI(baseurl+ Constants.ServiceUris.KEYWORD_TOPTEN);

		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<KeywordResponseVO> response = 
				restTemplate.getForEntity(uri.toString(), KeywordResponseVO.class);
				
		LOGGER.debug("keyword list result {}" , response.getBody());
	}
}
