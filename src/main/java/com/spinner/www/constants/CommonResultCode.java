package com.spinner.www.constants;

public enum CommonResultCode {

    /**
     * 성공 처리
     */
    SUCCESS(20000, "요청 성공"),

    /**
     * 로그인이 안되어있어 권한이 없는경우
     */
    UNAUTHORIZED(40101, "권한이 없습니다."),

    /**
     * 로그인은 되어 있으나 접근 권한이 없는 경우(ex: 게시글의 작성자와 수정자가 불일치)
     */
    FORBIDDEN(40301, "올바르지 않은 접근입니다."),

    NO_LOGIN(1000, "로그인이 필요합니다."),

    /**
     * 데이터 중복
     */
    DUPLICATE(40900, "데이터 중복"),

    /**
     * 신고 중복
     */
    DUPLICATE_REPORT(40901, "이미 신고한 게시물입니다."),

    /**
     * 스터디 인원수 유효성 오류 (2명 이상, 15명 이하)
     */
    INVALID_STUDY_MEMBER_COUNT(41008, "스터디는 최소 2명 이상, 최대 15명까지 가능합니다."),

    /**
     * 투표 항목 밸리데이션 하나 이상
     */
    INVALID_VOTE_ITEM_COUNT_MIN_ONE(41000, "투표 항목은 하나 이상 있어야 합니다."),

    /**
     * 투표 항목 벨리데이션 다섯 개 초과
     */
    INVALID_VOTE_ITEM_COUNT_MAX_FIVE(41001, "투표 항목 개수는 다섯 개를 넘을 수 없습니다."),

    /**
     * [싱글 투표] 다중 선택 시
     */
    NOT_MULTIPLE_VOTE(41002, "다중 선택이 불가능한 투표입니다."),

    /**
     * 투표가 끝났을 경우
     */
    END_VOTE(41003, "이미 투표 기간이 마감된 투표입니다."),

    /**
     * 투표자와 로그인 사용자가 일치하지 않는 경우
     */
    VOTER_USER_MISMATCH(41004, "투표자와 로그인 이용자가 일치하지 않습니다."),

    /**
     * 투표 리스트가 없는 경우
     */
    VOTE_NOT_FOUND(41005, "투표를 찾을 수 없습니다."),

    /**
     * 투표 결과를 확인할 수 없는 권한의 경우
     */
    VOTE_RESULT_NOT_ACCESS(41006, "투표 결과를 확인할 수 없습니다."),

    /**
     * 중복 투표의 경우
     */
    VOTE_NOT_UPDATE(41007, "이미 투표하셨습니다."),

    /**
     * 서버 에러
     */
    ERROR(50000, "서버 에러"),

    /**
     * DB 조회 후 데이터 미존재
     */
    DATA_NOT_FOUND(50001, "데이터를 찾을 수 없음"),

    /**
     * 이메일 세션 조회 실패
     */
    EMAIL_SESSION_NOT_FOUND(5003, "세션이 없습니다. 이메일 인증 다시해주세요."),
    
    /**
     * 파일 업로드 실패
     */
    FILE_UPLOAD_FAIL(50002, "파일 업로드에 실패했습니다"),

    /**
     * 비밀번호 불일치
     */
    PASSWORD_MISMATCH(1003, "비밀번호가 일치하지 않습니다."),

    /**
     * 비밀번호 유효성 체크
     */
    INVALID_PASSWORD_FORMAT(1004, "비밀번호는 영문,숫자 포함 8자리에서 20자리로 작성해주세요"),

    /**
     * 스터디명 유효성 체크
     */
    INVALID_STUDY_NAME_FORMAT(1005, "스터디명은 3자~10자이며 특수문자는 쓸 수 없습니다."),

    /**
     * 런타임 오류 익셉션
     */
    RUNTIME_EXCEPTION(50003, "런타임 오류 익셉션"),

    MARKETING_CONSENT_MISSING(40001, "마케팅 수신 동의 미체크"),

    BAD_REQUEST(40003, "올바르지 못한 요청입니다."),

    INVALID_STUDY_INTRO_LENGTH(1006, "스터디 소개는 최소 10자 이상 100자 이내입니다.");


    /**
     * 변수 설정
     */
    private final int code;
    private final String message;

    /**
     * 생성자 생성
     * @param code    int
     * @param message String
     */
    CommonResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 오류 코드를 반환한다
     * @return String
     */
    public int code() {
        return code;
    }

    /**
     * 오류 메시지를 반환한다
     * 치환 문자("{}") 및 공백 문자 제거
     * @return String
     */
    public String message() {
        return message.replace("{}", "").trim();
    }
}
