package com.spinner.www.config;

import com.spinner.www.config.handler.CustomerLogoutHandler;
import com.spinner.www.users.dto.SessionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security
 * 스프링 시큐리티 설정
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /** 추 후 url 추가 예정 */
    private static  final String[] PERMIT_URL_ARRAY = {};

    private final SessionInfo sessionInfo;
    private final CustomerLogoutHandler customerLogoutHandler;

    /**
     * 비밀번호 단방향 암호화
     * [note] 복호화가 불가능하며 입력된 비밀번호를 암호화하여 비교 -> 암호화 알고리즘 재검토 필요
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 스프링 시큐리티 암호화 설정
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler((request, response, authentication) -> {
                            sessionInfo.logout();
                        })
                        .logoutSuccessHandler(customerLogoutHandler)
                        .deleteCookies("JSESSIONID", "refreshToken")
                );
        return http.build();
    }
}
