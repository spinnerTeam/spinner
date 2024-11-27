package com.spinner.www.member.service;

import com.spinner.www.member.repository.SocialRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SocialServiceImpl implements SocialService{

    private final SocialRepo socialRepo;

    /**
     * sub 유무
     * @param sub String
     * @return boolean
     */
    @Override
    public boolean existsBySocialSub(String sub) {
        return socialRepo.existsBySocialSub(sub);
    }
}
