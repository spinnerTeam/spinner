package com.spinner.www.chat.repository;

import com.spinner.www.chat.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomMemberRepo extends JpaRepository<ChatRoomMember, Long> {
}
