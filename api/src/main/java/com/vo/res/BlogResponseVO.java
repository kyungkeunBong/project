package com.vo.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.vo.DocumentVO;
import com.vo.MetaVO;

import java.util.List;

@Setter
@Getter
@ToString
public class BlogResponseVO {	
	private MetaVO meta;
	private List<DocumentVO> documents;
}
