package com.spinner.www.chat.entity;

import com.spinner.www.chat.constants.MessageType;
import com.spinner.www.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "message")
@Comment("채팅 메시지 테이블")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("채팅 메시지 PK")
    private Long messageIdx;

    @Comment("메시지 내용")
    private String messageContent;
    @Enumerated(EnumType.STRING)
    @Comment("메시지 타입")
    private MessageType messageType;
    @Comment("메시지 보낸 날짜")
    private LocalDate messageSendDate;
    @Comment("메시지 보낸 시간")
    private LocalTime messageSendTime;

    @ManyToOne
    @JoinColumn(name = "memberIdx")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "chatRoomIdx")
    private ChatRoom chatRoom;
}
