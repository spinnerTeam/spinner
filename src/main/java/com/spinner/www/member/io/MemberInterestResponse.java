package com.spinner.www.member.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
public class MemberInterestResponse {
    private Long idx;
    private String name;
    @JsonProperty("isSelected")
    private boolean selected;
}
