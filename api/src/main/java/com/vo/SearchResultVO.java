package com.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class SearchResultVO {
	private MetaVO meta;
	private List<DocumentVO> documents;
}
