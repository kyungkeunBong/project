package com.kakaobank.blog.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NaverItemVO {
	String title; // 블로그 글 제목
	String link; // 블로그 글 URL  url
	String description; // 블로그 글 내용 contents
	String bloggername; // 블로거의 이름 
	String bloggerlink; // 블로거의 링크
	String postdate; //yyyymmdd datetime 변환필요
}
