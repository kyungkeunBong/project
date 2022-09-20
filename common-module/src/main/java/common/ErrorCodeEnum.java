package common;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public enum ErrorCodeEnum {
	SUCCESS("0000" , "성공"),
	INPUT_ERROR("1000" , "필수값 부족"),
	KAKAO_SERVER_ERROR("1001", "KAKAO SERVER ERROR"),
	NAVER_SERVER_ERROR("1002", "NAVER SERVER ERROR"),
	URL_ENCODE_ERROR("1003", "com.connector 인코딩 에러"),
	KAKAO_CLIENT_ERROR("1004", "KAKAO_CLIENT_ERROR"),
	KAKAO_UNDEFINED_ERROR("1005", "KAKAO SERVER ERROR"),
	KAKAO_REST_API_KEY_ERROR("1006", "HEADER KEY ERROR"),
	KAKAO_INTERNAL_SERVER_ERROR("1007", "KAKAO INTERNAL SERVER ERROR");

	private String codeNum;
	private String description;
	ErrorCodeEnum(String codeNum, String description) {
		this.codeNum = codeNum;
		this.description = description;
	}	
}
