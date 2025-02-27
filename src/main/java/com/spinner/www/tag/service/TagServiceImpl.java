package com.spinner.www.tag.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.tag.dto.TagDto;
import com.spinner.www.tag.entity.Tag;
import com.spinner.www.tag.io.TagCreateRequest;
import com.spinner.www.tag.io.TagCreateResponse;
import com.spinner.www.tag.mapper.TagMapper;
import com.spinner.www.tag.repository.TagRepo;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepo tagRepo;
    private final TagMapper tagMapper;

    @Override
    public ResponseEntity<CommonResponse> insertTag(TagCreateRequest tagCreateRequest) {

        TagDto tagDto = tagMapper.toTagDto(tagCreateRequest);

        Tag tag = Tag.create(tagDto);
        tagRepo.save(tag);
        TagCreateResponse tagCreateResponse = tagMapper.toTagCreateResponse(tag);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(tagCreateResponse), HttpStatus.OK);
    }
}
