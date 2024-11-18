package com.spinner.www.common.io;

import lombok.experimental.SuperBuilder;
import lombok.Getter;
import lombok.Setter;

@SuperBuilder
@Getter
@Setter
public class BaseResponse {
    protected String createdDate;
    protected String modifiedDate;
}
