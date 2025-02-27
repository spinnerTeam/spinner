package com.spinner.www.chat.constants;

public enum ChatRoomType {

    DIRECT("1:1 채팅방"),
    GROUP("단체 채팅방");

    private final String description;

    ChatRoomType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
