package com.kakaobank.blog.vo;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SearchResultVO {
	private MetaVO meta;
	private List<DocumentVO> documents;
}
