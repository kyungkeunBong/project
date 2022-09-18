package com.kakaobank.blog.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MetaVO {
	int total_count; // 검색된 문서 수
	int pageable_count; // total_count 중 노출 가능 문서 수
	@JsonProperty(value = "is_end")
	boolean is_end; // 현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음 
}
