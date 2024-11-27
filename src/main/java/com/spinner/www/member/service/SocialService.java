package com.spinner.www.member.service;


public interface SocialService {

    /**
     * sub 유무
     * @param sub String
     * @return boolean
     */
    boolean existsBySocialSub(String sub);
}
