package com.kakaobank.blog.vo.res;

import java.util.List;

import com.kakaobank.blog.vo.DocumentVO;
import com.kakaobank.blog.vo.MetaVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BlogResponseVO {	
	private MetaVO meta;
	private List<DocumentVO> documents;
}
