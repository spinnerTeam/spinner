package com.spinner.www.vote.mapper;

import com.spinner.www.vote.dto.VoteCreateDto;
import com.spinner.www.vote.entity.VoteItem;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface VoteMapper {
    VoteItem voteCreateDtoToVoteItem(VoteCreateDto voteCreateDto);
}
