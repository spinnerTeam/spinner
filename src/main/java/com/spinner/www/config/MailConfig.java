package com.spinner.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * 이메일 인증 서비스 설정파일
 */
@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setUsername("wjddus5533@gmail.com");
        javaMailSender.setPassword("lbzz yrip ruxz rcju");
        javaMailSender.setPort(587);
        javaMailSender.setJavaMailProperties(getMailProperties());
        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.ssl.enable", "false");
        properties.setProperty("mail.debug", "false");
        properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
        return properties;
    }

}
