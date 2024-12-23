package com.spinner.www.member.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoin {

    private String password;
    private String nickName;
    private String name;
    private String birth;

    private MultipartFile file;
    private List<Boolean> marketingList;
    private List<Long> menuList;

    public MemberJoin(MemberJoin memberJoin, String password){
        this.password = password;
        this.nickName = memberJoin.getNickName();
        this.name = memberJoin.getName();
        this.birth = memberJoin.getBirth();
        this.file = memberJoin.getFile();
        this.marketingList = memberJoin.getMarketingList();
        this.menuList = memberJoin.getMenuList();
    }
}
