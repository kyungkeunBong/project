package com.connector;

import common.AbstractConnector;
import common.ConnectorAPIEnum;
import common.Constants;
import exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import com.vo.SearchNaverResultVO;
import com.vo.SearchResultVO;
import com.vo.SearchVO;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

@Component
public class Connector extends AbstractConnector {
//	private final String kakaoHost = "https://dapi.kakao.com";
//	private final String kakaoRestApiKey = "KakaoAK bab1235ec50b97cb58049b4633a7849b";
//	private final String naverHost = "https://openapi.naver.com";
//	private final String naverClientId = "aSfBrdAq9j0pNd40Igwy";
//	private final String naverClientSecret = "beKmWfKWcv";
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	// 블로그 검색 
	public SearchResultVO search(SearchVO searchVO, String apiKey) throws CommonException{
		String resourceUrl = ConnectorAPIEnum.BLOG_SEARCH.getUrl();
		
		// 인풋값 세팅
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("query", searchVO.getQuery());
		queryParams.add("sort", searchVO.getSort());
		queryParams.add("page", String.valueOf(searchVO.getPage()));
		queryParams.add("size", String.valueOf(searchVO.getSize()));

		LOGGER.debug("queryParams : {}",queryParams.toString());
		// api url 세팅 
		URI apiUrl = createRequestUrl(Constants.RestApiHost.KAKAO_HOST,resourceUrl, null, queryParams);
				
		HttpEntity<?> requestEntity = new HttpEntity<>(getKakaoHeader(apiKey));
		
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
		URI apiUrl = createRequestUrl(Constants.RestApiHost.NAVER_HOST,resourceUrl, null, queryParams);
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
}
