package org.hh99.tmomi.global.message;

public enum ErrorMessage {
    EXIST_USER_ERROR_MESSAGE("존재하지 않는 사용자입니다."),
    PASSWORD_MISMATCH_ERROR_MESSAGE("로그인에 실패하였습니다."),
    EMAIL_FORMAT_ERROR_MESSAGE("올바른 이메일 형식이 아닙니다."),
    PASSWORD_VALIDATION_ERROR_MESSAGE("비밀번호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자, 숫자, 특수문자로 구성되어야 합니다.");

    private final String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

