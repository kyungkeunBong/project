package com.kakaobank.blog;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

import com.kakaobank.blog.common.ConnectorAPIEnum;
import com.kakaobank.blog.common.ErrorCodeEnum;
import com.kakaobank.blog.exception.CommonException;
import com.kakaobank.blog.vo.SearchNaverResultVO;
import com.kakaobank.blog.vo.SearchResultVO;
import com.kakaobank.blog.vo.SearchVO;

@Component
public class Connector {
	private final String kakaoHost = "https://dapi.kakao.com";
	private final String kakaoRestApiKey = "KakaoAK bab1235ec50b97cb58049b4633a7849b";
	private final String naverHost = "https://openapi.naver.com";
	private final String naverClientId = "aSfBrdAq9j0pNd40Igwy";
	private final String naverClientSecret = "beKmWfKWcv";
	
	
	// 블로그 검색 
	public SearchResultVO search(SearchVO searchVO) throws CommonException{
		String resourceUrl = ConnectorAPIEnum.BLOG_SEARCH.getUrl();
		
		// 인풋값 세팅
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("query", searchVO.getQuery());
		queryParams.add("sort", searchVO.getSort());
		queryParams.add("page", String.valueOf(searchVO.getPage()));
		queryParams.add("size", String.valueOf(searchVO.getSize()));
		
		// api url 세팅 
		URI apiUrl = createRequestUrl(kakaoHost,resourceUrl, null, queryParams);
				
		HttpEntity<?> requestEntity = new HttpEntity<>(getKakaoHeader());
		
		try {
			ResponseEntity<SearchResultVO> result = 
					getRestTemplate().exchange(apiUrl, HttpMethod.GET, requestEntity, SearchResultVO.class);
			return result.getBody();
		}catch(RestClientException e) {
			handleError(e);
		}
		return null;
	}
	
	public SearchNaverResultVO naverSearch(SearchVO searchVO) throws CommonException, UnsupportedEncodingException{
		String resourceUrl = ConnectorAPIEnum.NAVER_BLOG_SEARCH.getUrl();
		
		// 인풋값 세팅
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("query", URLEncoder.encode(searchVO.getQuery(), "UTF-8"));
		// sim 정확도순, date 날짜순
		if("accuracy".equals(searchVO.getSort())) {
			searchVO.setSort("sim");
		}else {
			searchVO.setSort("date");
		}
		queryParams.add("sort", searchVO.getSort());
		
		// naver max = 10
		if(searchVO.getSize() > 10) {
			searchVO.setSize(10);
		}
		// 검색 시작 위치 1 = 1, 2=11, 3=21
		int start = (searchVO.getPage()-1)*searchVO.getSize()+ 1;
		queryParams.add("start", String.valueOf(start));		
		queryParams.add("display", String.valueOf(searchVO.getSize()));
		
		// api url 세팅 
		URI apiUrl = createRequestUrl(naverHost,resourceUrl, null, queryParams);
		HttpEntity<?> requestEntity = new HttpEntity<>(getNaverHeader());
		
		try {
			ResponseEntity<SearchNaverResultVO> result = 
					getRestTemplate().exchange(apiUrl, HttpMethod.GET, requestEntity, SearchNaverResultVO.class);
			return result.getBody();
		}catch(RestClientException e) {
			handleError(e);
		}
		return null;
	}
	
	public URI createRequestUrl(String reqHost, 
			String resourceUrl, 
			Map<String, String> urlVariables, 
			MultiValueMap<String, String> queryParams) 
					throws CommonException{
		UriTemplate uriTemplate = new UriTemplate(reqHost + resourceUrl);
		String apiUrl;
		if(urlVariables == null) {
			// get 방식 대응
			apiUrl = uriTemplate.toString();
		}else {
			// post 방식 대응
			apiUrl = uriTemplate.expand(urlVariables).toString();
		}
		
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(apiUrl);
		
		if(queryParams != null) {
			for(Entry<String, List<String>> entrySet : queryParams.entrySet()) {
				if(entrySet.getValue().get(0) != null) {
					try {
						uriBuilder.queryParam(entrySet.getKey(), URLEncoder.encode(entrySet.getValue().get(0), "UTF-8"));
					}catch(Exception e){
						throw new CommonException(ErrorCodeEnum.URL_ENCODE_ERROR);
					}
				}
			}
		}		
		URI uri = uriBuilder.build(true).toUri();
		return uri;
	}
	
	public HttpHeaders getNaverHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Naver-Client-Id",naverClientId);
		headers.set("X-Naver-Client-Secret",naverClientSecret);
		return headers;
	}
	
	public HttpHeaders getKakaoHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization",kakaoRestApiKey);
		return headers;
	}
	
	public RestTemplate getRestTemplate() {
		CloseableHttpClient httpClient = 
				HttpClients.custom().setMaxConnTotal(2).setMaxConnPerRoute(10).build();
		return createCommonRestTemplate(httpClient);		
	}
	
	public RestTemplate createCommonRestTemplate(CloseableHttpClient httpClient) {
		HttpComponentsClientHttpRequestFactory requestFactory =
				getHttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		restTemplate.getMessageConverters().add(jacksonSupportsMoreType());
		return restTemplate;		
	}
	
	private HttpComponentsClientHttpRequestFactory 
		getHttpComponentsClientHttpRequestFactory(CloseableHttpClient httpClient) {
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		requestFactory.setConnectionRequestTimeout(3000); // 타임아웃 30초
		requestFactory.setReadTimeout(3000); 
		return requestFactory;
	}
	
	private HttpMessageConverter jacksonSupportsMoreType() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
		return converter;
	}
	
	public void handleError(RestClientException e) throws CommonException{
		if(e instanceof RestClientException) {
			ErrorCodeEnum errorCodeEnum = null;
			switch(((RestClientResponseException)e).getRawStatusCode()) {
			
				case 400:
				case 401:
				case 403:
				case 404:
					errorCodeEnum = ErrorCodeEnum.KAKAO_CLIENT_ERROR;
					break;
				case 500:
				case 503:
					errorCodeEnum = ErrorCodeEnum.KAKAO_INVOCATION_ERROR;
					break;
				default:
					errorCodeEnum = ErrorCodeEnum.KAKAO_UNDEFINED_ERROR;				
			}
			throw new CommonException(errorCodeEnum);
		}else {
			throw new CommonException(ErrorCodeEnum.KAKAO_UNDEFINED_ERROR);
		}
	}
}
