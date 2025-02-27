package com.spinner.www.chat.entity;

import com.spinner.www.chat.constants.ChatRoomType;
import com.spinner.www.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "chat_room")
@Comment("채팅방 테이블")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("채팅방 PK")
    private Long chatRoomIdx;

    @Comment("채팅방 이름")
    private String chatRoomName;

    @Comment("채팅방 타입")
    @Enumerated(EnumType.STRING)
    private ChatRoomType chatRoomType;


    public static ChatRoom insertDirectChatRoom(String chatRoomName){
        return ChatRoom.builder()
                .chatRoomName(chatRoomName)
                .chatRoomType(ChatRoomType.DIRECT)
                .build();

    }
}
