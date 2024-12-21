package com.spinner.www.member.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MemberJoin {

    private String password;
    private String nickName;
    private String name;
    private String birth;

    private MultipartFile file;
    private List<Boolean> marketingList;
    private List<Integer> menuList;
}
