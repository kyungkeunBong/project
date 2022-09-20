package common;

import java.net.URI;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import exception.CommonException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

public class AbstractConnector {
	public URI createRequestUrl(String reqHost, 
			String resourceUrl, 
			Map<String, String> urlVariables, 
			MultiValueMap<String, String> queryParams) 
					throws CommonException {
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
		headers.set(Constants.RestApiHost.NAVER_ID, Constants.RestApiKeys.NAVER_CLIENT_ID);
		headers.set(Constants.RestApiHost.NAVER_SERET, Constants.RestApiKeys.NAVER_CLIENT_SECRET);
		return headers;
	}
	
	public HttpHeaders getKakaoHeader(String apiKey) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(Constants.HeaderValues.HEADER_KEY,apiKey);
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
					errorCodeEnum = ErrorCodeEnum.KAKAO_INTERNAL_SERVER_ERROR;
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
