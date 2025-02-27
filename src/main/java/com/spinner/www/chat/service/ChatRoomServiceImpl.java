package com.spinner.www.chat.service;

import com.spinner.www.chat.dto.ChatRoomMemberDto;
import com.spinner.www.chat.entity.ChatRoom;
import com.spinner.www.chat.entity.ChatRoomMember;
import com.spinner.www.chat.io.CreateChatRoom;
import com.spinner.www.chat.repository.ChatRoomMemberRepo;
import com.spinner.www.chat.repository.ChatRoomRepo;
import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.member.dto.MemberDto;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.mapper.MemberMapper;
import com.spinner.www.member.service.MemberService;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomRepo chatRoomRepo;
    private final MemberService  memberService;
    private final ChatRoomMemberRepo chatRoomMemberRepo;

    private static final ConcurrentHashMap<Long, ChannelTopic> CONCURRENT_HASH_MAP = new ConcurrentHashMap<>();

    /**
     * 1:1 채팅방 생성
     * @param createChatRoom
     * @return
     */
    @Override
    public ResponseEntity<CommonResponse> createDirectChatRoom(CreateChatRoom createChatRoom) {

        // 보낸 사람
        Member sendMember = memberService.getMember(createChatRoom.getSendIdx());
        Member fromMember = memberService.getMember(createChatRoom.getFromIdx());
        String chatRoomName = sendMember.getMemberNickname() + " , " + fromMember.getMemberNickname();

        // 채팅방 생성
        ChatRoom chatRoom =
        chatRoomRepo.save(ChatRoom.insertDirectChatRoom(chatRoomName));

        ChatRoomMemberDto sendDto = new ChatRoomMemberDto(sendMember, chatRoom);
        ChatRoomMemberDto fromDto = new ChatRoomMemberDto(fromMember, chatRoom);

        // 채팅방 멤버 추가
        ChatRoomMember sendChatRoomMember = ChatRoomMember.insertChatRoomMember(sendDto);
        ChatRoomMember fromChatRoomMember = ChatRoomMember.insertChatRoomMember(fromDto);
        chatRoomMemberRepo.save(sendChatRoomMember);
        chatRoomMemberRepo.save(fromChatRoomMember);

        ChannelTopic topic = new ChannelTopic(String.valueOf(chatRoom.getChatRoomIdx()));
        CONCURRENT_HASH_MAP.putIfAbsent(chatRoom.getChatRoomIdx(), topic);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(chatRoom.getChatRoomIdx()), HttpStatus.OK);
    }
}
