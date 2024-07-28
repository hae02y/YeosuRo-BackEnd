package greenjangtanji.yeosuro.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    JWT_TOKEN_ERROR (401, "토큰 오류로 UserId 추출 불가"),
    BOARD_NOT_FOUND(404, "게시글 정보를 찾을 수 없음"),
    CATEGORY_NOT_FOUND (404, "게시글 카테고리 정보를 찾을 수 없음"),

    UNKNOWN_ERROR(1001, "토큰이 존재하지 않습니다."),
    WRONG_TYPE_TOKEN(1002, "잘못된 형식의 토큰입니다."),
    EXPIRED_TOKEN(1003, "만료된 토큰입니다."),
    UNSUPPORTED_TOKEN(1004, "변조된 토큰입니다."),
    ACCESS_DENIED(1005, "권한이 없습니다.");

    private final int status;

    private final String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
