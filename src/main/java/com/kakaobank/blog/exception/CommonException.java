package com.kakaobank.blog.exception;

import com.kakaobank.blog.common.ErrorCodeEnum;

public class CommonException extends Exception {

	public CommonException(ErrorCodeEnum codeEnum) {
		super(codeEnum.getCodeNum() + "|" + codeEnum.getDescription());
	}
}
