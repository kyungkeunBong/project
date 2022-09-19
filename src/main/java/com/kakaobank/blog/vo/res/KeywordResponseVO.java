package com.kakaobank.blog.vo.res;

import java.util.List;

import com.kakaobank.blog.repository.KeywordTopten;
import com.kakaobank.blog.vo.DocumentVO;
import com.kakaobank.blog.vo.MetaVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class KeywordResponseVO {	
	List<KeywordTopten> keywords;
}
