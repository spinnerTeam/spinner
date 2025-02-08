package com.spinner.www.tag.mapper;

import com.spinner.www.tag.dto.TagDto;
import com.spinner.www.tag.entity.Tag;
import com.spinner.www.tag.io.TagCreateRequest;
import com.spinner.www.tag.io.TagCreateResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDto toTagDto(TagCreateRequest tagCreateRequest);
    TagCreateResponse toTagCreateResponse(Tag tag);

}
