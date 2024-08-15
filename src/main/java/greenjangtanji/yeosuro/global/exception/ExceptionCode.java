package greenjangtanji.yeosuro.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    JWT_TOKEN_ERROR (401, "토큰 오류로 UserId 추출 불가"),
    BOARD_NOT_FOUND(404, "게시글 정보를 찾을 수 없음"),
    REPLY_NOT_FOUND (404, "댓글 정보를 찾을 수 없음"),
    NOT_FOUND(404, "정보를 찾을 수 없음"),
    CATEGORY_NOT_FOUND (404, "게시글 카테고리 정보를 찾을 수 없음"),
    USER_NOT_FOUND(404, "유저 정보를 찾을 수 없음"),
    USER_NOT_ACTIVE(403, "탈퇴한 회원입니다."),
    DUPLICATE_EMAIL_ERROR (409, "이메일 중복"),
    DUPLICATE_NICKNAME_ERROR (409, "닉네임 중복"),
    DUPLICATE_ERROR (409, "중복된 요청"),

    FILE_UPLOAD_ERROR(413, "파일 업로드 실패"),
    FILE_DELETE_ERROR(500, "파일 삭제 실패"),
    FILE_FORMAT_ERROR(415, "파일 확장자 오류"),
    FILE_SIZE_ERROR (413, "최대 업로드 개수 초과"),
    FILE_NOT_FOUND(404, "파일을 찾을 수 없음"),

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
