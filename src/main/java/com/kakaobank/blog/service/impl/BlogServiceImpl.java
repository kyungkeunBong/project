package com.kakaobank.blog.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kakaobank.blog.common.ErrorCodeEnum;
import com.kakaobank.blog.connector.Connector;
import com.kakaobank.blog.dto.KeywordDto;
import com.kakaobank.blog.exception.CommonException;
import com.kakaobank.blog.repository.KeywordTopten;
import com.kakaobank.blog.repository.WordRepository;
import com.kakaobank.blog.service.BlogService;
import com.kakaobank.blog.vo.DocumentVO;
import com.kakaobank.blog.vo.MetaVO;
import com.kakaobank.blog.vo.NaverItemVO;
import com.kakaobank.blog.vo.SearchNaverResultVO;
import com.kakaobank.blog.vo.SearchResultVO;
import com.kakaobank.blog.vo.SearchVO;
import com.kakaobank.blog.vo.req.BlogRequestVO;
import com.kakaobank.blog.vo.res.BlogResponseVO;
import com.kakaobank.blog.vo.res.KeywordResponseVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService{
	private final Connector blogConnector;
	private final WordRepository wr;
	
	@Override
	public KeywordResponseVO keywordTop(){
		List<KeywordTopten> keywords = wr.findToptenKeyword();
		System.out.println("########## db find");
		KeywordResponseVO response = new KeywordResponseVO();
		System.out.println("########## db find " + keywords.toString());
		List<KeywordDto> resultList = new ArrayList<>();
		KeywordDto result = new KeywordDto();
		for(KeywordTopten keyword : keywords) {
			result.setCount(keyword.getCount());
			result.setKeyword(keyword.getKeyword());
			resultList.add(result);
		}
		response.setKeywords(resultList);
		return response;
	}
	
	@Override
	public BlogResponseVO searchBlog(BlogRequestVO requestBody) 
			throws CommonException, ParseException, Exception{
		
		// 필수값 체크 param에서 필수값으로 해서 의미는 없음..
		// 이곳에.. 값체크같은 로직이 들어갈수 있음
		if(requestBody.getQuery() == null) {
			throw new CommonException(ErrorCodeEnum.INPUT_ERROR);
		}
				
		
		// input vo 생성
		SearchVO search = new SearchVO();
		search.setQuery(requestBody.getQuery());
		if(requestBody.getSort() == null) {
			search.setSort("accuracy");
		}
		// 숫자형은 안들어올 경우 디폴트 값 세팅
		if(requestBody.getPage() == 0) {
			search.setPage(1);
		}
		if(requestBody.getSize() == 0) {
			search.setSize(10);
		}
		
		SearchResultVO searchResultVO = new SearchResultVO();
		BlogResponseVO response = new BlogResponseVO();
		
		try {
			if(requestBody.getNaverId() != null) {
				throw new CommonException(ErrorCodeEnum.KAKAO_SERVER_ERROR);
			}
			searchResultVO = blogConnector.search(search, requestBody.getRestApiKey());
			BeanUtils.copyProperties(searchResultVO, response);
		}catch(Exception e) {
			if("1001|KAKAO SERVER ERROR".equals(e.getMessage())) {
				SearchNaverResultVO naverResult = blogConnector.naverSearch(search);		
				response.setMeta(setMeta(naverResult));
				response.setDocuments(setDocument(naverResult));
			}else {
				throw new CommonException(ErrorCodeEnum.KAKAO_UNDEFINED_ERROR);
			}
			
		}
		
		return response;		
	}
	private ArrayList<DocumentVO> setDocument(SearchNaverResultVO naverResult) throws ParseException {
		ArrayList<DocumentVO> documents = new ArrayList<>();
		for(NaverItemVO naverItemVO : naverResult.getItems()) {
			DocumentVO document = new DocumentVO();
			document.setTitle(naverItemVO.getTitle());
			document.setUrl(naverItemVO.getLink());
			document.setContents(naverItemVO.getDescription());
			// 시간 변환 yyyymmdd -> ISO타입으로
			String naverDate = naverItemVO.getPostdate(); // yyyymmdd
		    SimpleDateFormat from = new SimpleDateFormat("yyyyMMdd");
		    Date date = new Date(from.parse(naverDate).getTime());
		    SimpleDateFormat to = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");		        
		    document.setDatetime(to.format(date));
		    documents.add(document);
		}
		return documents;
	}
	private MetaVO setMeta(SearchNaverResultVO naverResult) {
		MetaVO meta = new MetaVO();
		meta.setTotal_count(naverResult.getTotal());
		meta.setPageable_count(naverResult.getDisplay());
		if(naverResult.getTotal() > naverResult.getDisplay()) {
			meta.set_end(false);
		}else {
			meta.set_end(true);
		}
		return meta;
	}
	
}
