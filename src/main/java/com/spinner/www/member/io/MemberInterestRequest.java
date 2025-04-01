package com.spinner.www.member.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
public class MemberInterestRequest {
    private Long idx;
    @JsonProperty("isSelected")
    private boolean selected;
}
