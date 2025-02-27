package com.spinner.www.chat.entity;

import com.spinner.www.chat.dto.ChatRoomMemberDto;
import com.spinner.www.common.entity.BaseEntity;
import com.spinner.www.member.dto.MemberDto;
import com.spinner.www.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;


@Entity
@Table(name = "chat_room_member")
@Comment("채팅방 멤버")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("채팅방 멤버 PK")
    private Long chatRoomMemberIdx;

    @ManyToOne
    @JoinColumn(name = "memberIdx")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "chatRoomIdx")
    private ChatRoom chatRoom;

    public static ChatRoomMember insertChatRoomMember(ChatRoomMemberDto memberDto){
        return ChatRoomMember.builder()
                .member(memberDto.getMember())
                .chatRoom(memberDto.getChatRoom())
                .build();

    }
}
