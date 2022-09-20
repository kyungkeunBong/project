package com.vo.res;

import com.dto.KeywordDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class KeywordResponseVO {
	List<KeywordDto> keywords;
}
