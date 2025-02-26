package com.spinner.www.study.constants;

import lombok.Getter;

@Getter
public enum StudyCategoryType {

    Backend("백엔드"), Frontend("프론트엔드"), Infra("인프라"), DB("DB"), etc("기타");

    private final String desc;

    StudyCategoryType(String desc) {
        this.desc = desc;
    }

}
