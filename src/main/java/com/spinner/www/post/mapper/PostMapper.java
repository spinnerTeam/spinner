package com.spinner.www.post.mapper;

import com.spinner.www.post.dto.PostGetDto;
import com.spinner.www.post.entity.Post;
import com.spinner.www.reply.mapper.ReplyMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReplyMapper.class})
public interface PostMapper {
//    Post postDtoToPost(PostGetDto postGetDto);
    @Mapping(target = "memberNickname", source = "member.memberNickname")
    PostGetDto postToPostGetDto(Post post);
//    List<PostGetDto> postListToPostGetDtoList(List<Post> posts);
}
