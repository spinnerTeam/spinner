package com.spinner.www.chat.dto;

import com.spinner.www.chat.entity.ChatRoom;
import com.spinner.www.member.dto.MemberDto;
import com.spinner.www.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomMemberDto {

    private Member member;
    private ChatRoom chatRoom;
}
