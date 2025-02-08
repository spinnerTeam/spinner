package com.spinner.www.tag.entity;

import com.spinner.www.tag.dto.TagDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tagIdx")
    private Long id;
    private String tagName;
    private int tagDepth;
    private String tagParent;

    public static Tag create(TagDto tagDto) {

        return Tag.builder()
                .tagName(tagDto.getTagName())
                .build();
    }
}
