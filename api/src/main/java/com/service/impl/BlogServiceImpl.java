package com.service.impl;

import common.ErrorCodeEnum;
import com.connector.Connector;
import com.dto.KeywordDto;
import com.entity.Keyword;
import exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.repository.KeywordTopten;
import com.repository.WordRepository;
import com.service.BlogService;
import com.vo.*;
import com.vo.req.BlogRequestVO;
import com.vo.res.BlogResponseVO;
import com.vo.res.KeywordResponseVO;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private final Connector blogConnector;
	private final WordRepository wr;
	
	@Override
	public KeywordResponseVO keywordTop(){
		List<KeywordTopten> keywords = wr.findToptenKeyword();
		KeywordResponseVO response = new KeywordResponseVO();
		List<KeywordDto> resultList = new ArrayList<>();
		
		for(KeywordTopten keyword : keywords) {
			KeywordDto result = new KeywordDto();
			result.setCount(keyword.getCount());
			result.setKeyword(keyword.getKeyword());
			resultList.add(result);
		}
		response.setKeywords(resultList);
		return response;
	}
	
	@Override
	public BlogResponseVO searchBlog(BlogRequestVO requestBody)
			throws CommonException, UnsupportedEncodingException, ParseException {
		
		// 필수값 체크 param에서 필수값으로 해서 의미는 없음..
		// 이곳에.. 값체크같은 로직이 들어갈수 있음
		if(requestBody.getQuery() == null) {
			throw new CommonException(ErrorCodeEnum.INPUT_ERROR);
		}

		// input com.vo 생성
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
		// 검색한 키워드 등록
		// db inert 실패시에도 검색값은 전달해줘야함.
		try {
			LOGGER.debug("현재까지 id 값 : {}", wr.count());
			Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+9"));
			calendar.setTimeInMillis(new Date().getTime());
			Keyword key = new Keyword(wr.count() + 1, requestBody.getQuery(), String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)));
			wr.save(key);
		}catch(Exception e){
			LOGGER.error(ErrorCodeEnum.KAKAO_DB_ERROR.getDescription());
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
