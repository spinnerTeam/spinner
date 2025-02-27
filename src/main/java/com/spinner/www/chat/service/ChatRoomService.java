package com.spinner.www.chat.service;


import com.spinner.www.chat.entity.ChatRoom;
import com.spinner.www.chat.io.CreateChatRoom;
import com.spinner.www.common.io.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface ChatRoomService {

    ResponseEntity<CommonResponse> createDirectChatRoom(CreateChatRoom createChatRoom);
}
