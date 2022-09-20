package com.vo.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BlogRequestVO extends CommonRequestVO {
	private String query; // 검색을 위한 질의어, 특정블로그만 검색 원할시 (url+blank+검색어)로 입력가능 필수값
	private String sort; // 정렬방식 accuracy(정확도), recency(최신순) default: accuracy
	private int page; // 1~50 default: 1
	private int size; // 페이지당 문서수 1~50 default 10
}
