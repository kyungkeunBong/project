package com.kakaobank.blog.vo.res;

import java.util.List;
import java.util.function.Supplier;

import com.kakaobank.blog.dto.KeywordDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class KeywordResponseVO {
	List<KeywordDto> keywords;
}
