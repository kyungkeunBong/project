package exception;


import common.ErrorCodeEnum;

public class CommonException extends Exception {

	public CommonException(ErrorCodeEnum codeEnum) {
		super(codeEnum.getCodeNum() + "|" + codeEnum.getDescription());
	}
}
