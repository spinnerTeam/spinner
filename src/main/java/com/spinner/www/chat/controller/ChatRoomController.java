package com.spinner.www.chat.controller;

import com.spinner.www.chat.io.CreateChatRoom;
import com.spinner.www.chat.service.ChatRoomService;
import com.spinner.www.common.io.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chatRoom")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    /**
     * 1:1 채팅방 생성
     * @param chatRoom
     * @return
     */
    @PostMapping("/directChatRoom")
    public ResponseEntity<CommonResponse> createChatRoom(@RequestBody CreateChatRoom chatRoom){
        return chatRoomService.createDirectChatRoom(chatRoom);
    }


}
