package com.kakaobank.blog.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class DocumentVO {
	String title; // 블로그 글 제목
	String contents; // 블로그 글내용
	String url; // 블로그 글 URL
	String blogname; // 블로그의 이름
	String thumbnail; // 검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음
	String datetime; // 블로그 글 작성시간, ISO 8601 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
}
