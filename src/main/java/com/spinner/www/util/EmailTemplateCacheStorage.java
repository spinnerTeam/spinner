package com.spinner.www.util;

import com.spinner.www.member.entity.EmailTemplate;
import com.spinner.www.member.repository.EmailTemplateRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailTemplateCacheStorage {

    private final Map<String, EmailTemplate> templateMap = new HashMap<>();
    private final EmailTemplateRepo emailTemplateRepo;

    /**
     * 전체 이메일 템플릿 조회
     */
    @PostConstruct
    public void getAllEmailTemplate(){
        List<EmailTemplate> templates = emailTemplateRepo.findAll();
        for(EmailTemplate template : templates){
            templateMap.put(String.valueOf(template.getEmailTemplateType()), template);
        }
    }

    /**
     * 템플릿 타입별 조회
     * @param emailTemplateType String
     * @return EmailTemplate
     */
    public EmailTemplate getEmailTemplate(String emailTemplateType){
        return templateMap.get(emailTemplateType);
    }
}
