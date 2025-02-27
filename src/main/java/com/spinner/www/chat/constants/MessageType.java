package com.spinner.www.chat.constants;

public enum MessageType {

    TEXT("텍스트"),
    IMAGE("이미지"),
    FILE("첨부파일");

    private final String description;

    MessageType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
