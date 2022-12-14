package com.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class SearchNaverResultVO {
	private String lastBuildDate;
	private int total; // 총수 MetaVO total_count
	private String start; // 
	private int display; // count MetaVO pageable_count 
	private List<NaverItemVO> items; 
	// if total > display is_end false
}
