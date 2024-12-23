package com.spinner.www.vote.mapper;

import com.spinner.www.vote.dto.VoteCreateDto;
import com.spinner.www.vote.dto.VoteSelectDto;
import com.spinner.www.vote.entity.Vote;
import com.spinner.www.vote.entity.VoteItem;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface VoteMapper {
    VoteSelectDto toVoteSelectDto(Long voteIdx);
}
