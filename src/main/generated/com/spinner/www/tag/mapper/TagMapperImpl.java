package com.spinner.www.tag.mapper;

import com.spinner.www.tag.dto.TagDto;
import com.spinner.www.tag.entity.Tag;
import com.spinner.www.tag.io.TagCreateRequest;
import com.spinner.www.tag.io.TagCreateResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-17T16:56:02+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class TagMapperImpl implements TagMapper {

    @Override
    public TagDto toTagDto(TagCreateRequest tagCreateRequest) {
        if ( tagCreateRequest == null ) {
            return null;
        }

        TagDto.TagDtoBuilder tagDto = TagDto.builder();

        tagDto.tagName( tagCreateRequest.getTagName() );

        return tagDto.build();
    }

    @Override
    public TagCreateResponse toTagCreateResponse(Tag tag) {
        if ( tag == null ) {
            return null;
        }

        TagCreateResponse.TagCreateResponseBuilder tagCreateResponse = TagCreateResponse.builder();

        return tagCreateResponse.build();
    }
}
